package net.mikoto.roxy.core.manager;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import groovy.lang.GroovyClassLoader;
import lombok.extern.log4j.Log4j2;
import net.mikoto.roxy.core.model.Config;
import net.mikoto.roxy.core.model.RoxyModel;
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
    private static final Map<String, Class<?>> modelMap = new HashMap<>();
    private static final GroovyClassLoader groovyClassLoader = new GroovyClassLoader();

    @Autowired
    public ModelManager(@NotNull Config config) throws IOException {
        // To make sure the dir is existed.
        createDir(config.getConfigPath());
        File configDir = new File(System.getProperty("user.dir") + config.getConfigPath());
        File[] modelFiles = configDir.listFiles(
                pathname -> pathname.isDirectory() || pathname.getAbsolutePath().endsWith(config.getModelSuffix())
        );
        File[] configFiles = configDir.listFiles(
                pathname -> pathname.isDirectory() || pathname.getAbsolutePath().endsWith(config.getConfigSuffix())
        );
        File[] mapperFiles = configDir.listFiles(
                pathname -> pathname.isDirectory() || pathname.getAbsolutePath().endsWith(config.getMapperSuffix())
        );

        // Looking for models.
        if (modelFiles != null && modelFiles.length > 0) {
            for (File file :
                    modelFiles) {
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

                JSONObject modelJson = JSONObject.parseObject(stringBuilder.toString());
                String modelName = modelJson.getString("modelName");
                modelMap.put(modelName, generateModelClass(config.getModelPackage(), modelJson));
                log.info("[Roxy] Found model -> " + modelName);
            }
        } else {
            log.warn("[Roxy] No model was found");
        }


    }

    public Class<?> getRawModel(String modelName) {
        return modelMap.get(modelName);
    }

    public Class<?>[] getRawModels() {
        return modelMap.values().toArray(new Class[0]);
    }

    public String[] getModelNames() {
        return modelMap.keySet().toArray(new String[0]);
    }

    public static Class<?> generateModelClass(String packageName, @NotNull JSONObject roxyModel) {
        StringBuilder classCodeBuilder = new StringBuilder();
        classCodeBuilder.append("package ").append(packageName).append(";"); // package [PackageName];
        classCodeBuilder.append("import javax.persistence.*;"); // import javax.persistence.*;
        classCodeBuilder.append("@Entity "); // @Entity
        classCodeBuilder.append("@Table(name = \"").append(roxyModel.get("tableName")).append("\") "); // @Table(name = "[TableName]")
        classCodeBuilder.append("public class ").append(roxyModel.get("modelName")).append(" extends ").append(RoxyModel.class.getName()).append(" {");
        // public class [ModelName] extends net.mikoto.roxy.core.model.RoxyModel

        // Fields foreach
        JSONArray fields = roxyModel.getJSONArray("field");
        for (int i = 0; i < fields.size(); i++) {
            JSONObject field = fields.getJSONObject(i);

            String fieldName = field.getString("name");
            if (fieldName.equals(roxyModel.get("idFieldName"))) {
                classCodeBuilder.append("@Id ");
            }

            String fieldColumnName = field.getString("columnName");
            if (fieldColumnName != null) {
                classCodeBuilder.append("@Column(name = \" + ").append(fieldColumnName).append(" + \") ");
            }

            classCodeBuilder.append("private ").append(field.getString("type")).append(" ").append(fieldName).append(";");
        }
        classCodeBuilder.append("}");
        return groovyClassLoader.parseClass(classCodeBuilder.toString());
    }
}
