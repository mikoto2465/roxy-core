package net.mikoto.roxy.core.manager;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import groovy.lang.GroovyClassLoader;
import lombok.extern.log4j.Log4j2;
import net.mikoto.roxy.core.model.AbstractHasAHashMapClass;
import net.mikoto.roxy.core.model.RoxyDataModel;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Iterator;

/**
 * @author mikoto
 * @date 2022/10/15
 * Create for core
 */
@Component("RoxyDataModelManager")
@Log4j2
public class DataModelManager extends AbstractHasAHashMapClass<Class<? extends RoxyDataModel>> {
    private static final GroovyClassLoader groovyClassLoader = new GroovyClassLoader();

    /**
     * Register a new model.
     *
     * @param modelName The model name.
     * @param modelClass The model class.
     */
    public void registerModel(String modelName, Class<? extends RoxyDataModel> modelClass) {
        super.put(modelName, modelClass);
    }

    public Iterator<Class<? extends RoxyDataModel>> getIterator() {
        return super.dataMap.values().iterator();
    }

    public String[] getModelNames() {
        return super.dataMap.keySet().toArray(new String[0]);
    }

    @SuppressWarnings({"CastCanBeRemovedNarrowingVariableType", "unchecked"})
    public static Class<? extends RoxyDataModel> generateModelClass(String packageName, @NotNull JSONObject roxyModel) {
        StringBuilder classCodeBuilder = new StringBuilder();
        classCodeBuilder.append("package ").append(packageName).append(";"); // package [PackageName];
        classCodeBuilder.append("import javax.persistence.*;"); // import javax.persistence.*;
        classCodeBuilder.append("@Entity "); // @Entity
        classCodeBuilder.append("@Table(name = \"").append(roxyModel.get("tableName")).append("\") "); // @Table(name = "[TableName]")
        classCodeBuilder.append("public class ").append(roxyModel.get("modelName")).append(" extends ").append(RoxyDataModel.class.getName()).append(" {");
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
        Class<?> clazz = groovyClassLoader.parseClass(classCodeBuilder.toString());
        return (Class<? extends RoxyDataModel>) clazz;
    }
}
