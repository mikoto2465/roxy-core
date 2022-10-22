package net.mikoto.roxy.core.manager;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.log4j.Log4j2;
import net.mikoto.roxy.core.model.Config;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static net.mikoto.roxy.core.util.FileUtil.createDir;

/**
 * @author mikoto
 * @date 2022/10/15
 * Create for core
 */
@Component("RoxyModelManager")
@Log4j2
public class ModelManager {
    private static final Map<String, JSONObject> rawModelJsonMap = new HashMap<>();

    @Autowired
    public ModelManager(@NotNull Config config) throws IOException {
        // To make sure the dir is existed.
        createDir(config.getModelPath());
        File dir = new File(System.getProperty("user.dir") + config.getModelPath());
        File[] files = dir.listFiles(
                pathname -> pathname.isDirectory() || pathname.getAbsolutePath().endsWith(config.getModelSuffix())
        );
        if (files != null && files.length > 0) {
            for (File file :
                    files) {
                StringBuilder stringBuilder = new StringBuilder();
                // Read single file
                try (
                        FileInputStream fileInputStream = new FileInputStream(file);
                        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader)
                ) {
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                }

                JSONObject jsonObject = JSONObject.parseObject(stringBuilder.toString());
                rawModelJsonMap.put(file.getName().replace(config.getModelSuffix(), ""), jsonObject);
                log.info("[Roxy] Found model: " + file.getName());
            }
        } else {
            log.warn("[Roxy] No model was found");
        }
    }

    public JSONObject getRawModelJson(String name) {
        return rawModelJsonMap.get(name);
    }

    public JSONObject[] getRawModelJsons() {
        return rawModelJsonMap.values().toArray(new JSONObject[0]);
    }
}
