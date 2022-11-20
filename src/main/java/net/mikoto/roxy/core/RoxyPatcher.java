package net.mikoto.roxy.core;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import lombok.extern.log4j.Log4j2;
import net.mikoto.roxy.core.algorithm.Algorithm;
import net.mikoto.roxy.core.algorithm.ShardableAlgorithm;
import net.mikoto.roxy.core.model.Config;
import net.mikoto.roxy.core.model.Resource;
import net.mikoto.roxy.core.model.RoxyModel;
import net.mikoto.roxy.core.model.Task;
import net.mikoto.roxy.core.model.config.ThreadPoolConfig;
import net.mikoto.roxy.core.model.network.HttpHandler;
import net.mikoto.roxy.core.model.network.resource.HttpTarget;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

@Log4j2
public class RoxyPatcher {
    private final RoxyModel roxyModel;
    private final Config config;
    private boolean startFlag = false;

    private final ThreadPoolExecutor threadPoolExecutor;

    public RoxyPatcher(@NotNull RoxyModel roxyModel, Config config) {
        this.roxyModel = roxyModel;
        this.config = config;
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

            List<Algorithm<?>> tasksList;

            Task task = roxyModel.getTask();
            // To check if it is a single thread task
            if (task.getTaskCount() != 1) {
                Algorithm<?> taskAlgorithm =task.getTaskAlgorithm();
                if (taskAlgorithm instanceof ShardableAlgorithm<?>) {
                    tasksList = new ArrayList<>(
                            List.of(
                                    ((ShardableAlgorithm<?>) taskAlgorithm).shard(task.getTaskCount())
                            )
                    );
                } else {
                    throw new RuntimeException("No support multi thread.");
                }
            } else {
                tasksList = new ArrayList<>();
                tasksList.add(task.getTaskAlgorithm());
            }

            for (Algorithm<?> currentTask :
                    tasksList) {
                threadPoolExecutor.execute(() -> {
                    String currentId = (String) currentTask.run();
                    while (currentId != null) {
                        Resource resource = (Resource) roxyModel.getResourcesAlgorithm().run();
                        HttpTarget httpTarget = (HttpTarget) resource.getResourceAlgorithm().run();
                        Map<String, String> paramsMap = new HashMap<>();
                        paramsMap.put("id", currentId);
                        log.info("Using resource -> " + resource.getResourceName() + " ; httpTarget -> " + httpTarget.getAddress() + " ; id -> " + paramsMap.get("id"));
                        currentId = (String) currentTask.run();
                    }
                });
            }
        }
    }
}
