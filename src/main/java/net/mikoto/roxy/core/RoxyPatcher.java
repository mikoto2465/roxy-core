package net.mikoto.roxy.core;

import com.alibaba.fastjson2.JSONObject;
import com.dtflys.forest.Forest;
import com.dtflys.forest.exceptions.ForestNetworkException;
import lombok.extern.log4j.Log4j2;
import net.mikoto.roxy.core.strategy.ShardableStrategy;
import net.mikoto.roxy.core.model.Resource;
import net.mikoto.roxy.core.model.RoxyModel;
import net.mikoto.roxy.core.model.Task;
import net.mikoto.roxy.core.model.config.ThreadPoolConfig;
import net.mikoto.roxy.core.model.network.resource.HttpTarget;
import net.mikoto.roxy.core.observer.Observable;
import net.mikoto.roxy.core.storage.Storage;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;

@Log4j2
public class RoxyPatcher extends Observable {
    private final RoxyModel roxyModel;
    private boolean startFlag = false;

    private final ThreadPoolExecutor threadPoolExecutor;

    public RoxyPatcher(@NotNull RoxyModel roxyModel) {
        this.roxyModel = roxyModel;
        ThreadPoolConfig threadPoolConfig = roxyModel.getTask().getThreadPoolConfig();
        threadPoolExecutor = new ThreadPoolExecutor(
                threadPoolConfig.getCorePoolSize(),
                threadPoolConfig.getMaximumPoolSize(),
                threadPoolConfig.getKeepAliveTime(),
                threadPoolConfig.getTimeUnit(),
                threadPoolConfig.getWorkQueue(),
                threadPoolConfig.getRejectedExecutionHandler()
        );
    }

    public void stop() {
        startFlag = false;
    }

    public void start() {
        if (!startFlag) {
            startFlag = true;

            List<Strategy<?>> tasksList;

            //
            Task task = roxyModel.getTask();
            // To check if it is a single thread task
            if (task.getTaskCount() != 1) {
                Strategy<?> taskStrategy =task.getTaskStrategy();
                if (taskStrategy instanceof ShardableStrategy<?>) {
                    tasksList = new ArrayList<>(
                            List.of(
                                    ((ShardableStrategy<?>) taskStrategy).shard(task.getTaskCount())
                            )
                    );
                } else {
                    throw new RuntimeException("No support multi thread.");
                }
            } else {
                tasksList = new ArrayList<>();
                tasksList.add(task.getTaskStrategy());
            }

            for (Strategy<?> currentTask :
                    tasksList) {
                threadPoolExecutor.execute(() -> {
                    String currentId = (String) currentTask.run();
                    while (currentId != null) {
                        // Prepare for the request.
                        Resource resource = (Resource) roxyModel.getResourcesStrategy().run();
                        HttpTarget httpTarget = (HttpTarget) resource.getResourceStrategy().run();
                        Map<String, String> paramsMap = new HashMap<>();
                        paramsMap.put("id", currentId);
                        String fullAddress = httpTarget.getFullAddress(paramsMap);

                        String response;
                        try {
                            // Do request
                            response = Forest.get(fullAddress).executeAsString();
                            log.info("Do request to -> " + fullAddress + "\n Result -> ");

                            // Do storage
                            for (Map.Entry<String, Storage> entry : roxyModel.getStorages().entrySet()){
                                log.info("Do store to -> " + entry.getKey());
                                try {
                                    entry.getValue().store(currentId, currentId, JSONObject.parse(response).getObject("body", roxyModel.getRoxyDataModelClass()));
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }

                        } catch (ForestNetworkException e) {
                            log.info("Do request to -> " + fullAddress + " -> " + e.getStatusCode());
                        }

                        super.notify(currentId);
                        currentId = (String) currentTask.run();
                    }
                });
            }
        }
    }

    public int getThreadCount() {
        return this.threadPoolExecutor.getActiveCount();
    }
}
