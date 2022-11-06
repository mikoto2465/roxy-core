package net.mikoto.roxy.core.model.network.server;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpTarget {
    private String address;
    private String route;
    private static final Pattern routeParam = Pattern.compile("\\$\\{\\w+?}");

    public String getFullAddress(@NotNull Class<?> modelClass, Object model) throws NoSuchFieldException, IllegalAccessException {
        String currentRoute = route;

        // Check if instance
        if (!modelClass.isInstance(model)) {
            throw new RuntimeException("The object is not the instance of the class");
        }

        // Replace the id
        if (currentRoute.contains("${id}")) {
            for (Field modelField :
                    modelClass.getDeclaredFields()) {
                if (modelField.isAnnotationPresent(Id.class)) {
                    currentRoute = currentRoute.replace("${id}", "${" + modelField.getName() + "}");
                    break;
                }
            }
        }

        Matcher matcher = routeParam.matcher(currentRoute);

        while (matcher.find()) {
            String matchString = matcher.group(0);
            Field field = modelClass.getDeclaredField(matchString.replace("${", "").replace("}", ""));
            field.setAccessible(true);
            currentRoute = currentRoute.replace(matchString, String.valueOf(field.get(model)));
        }

        return address + currentRoute;
    }
}
