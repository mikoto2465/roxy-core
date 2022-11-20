package net.mikoto.roxy.core.model.network.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpTarget implements VariableResource<String>{
    private String address;
    private String route;
    private static final Pattern routeParam = Pattern.compile("\\$\\{\\w+?}");

    public HttpTarget(String address) {
        this.address = address;
    }

    public String getFullAddress(Map<?, ?> args) {
        String currentRoute = route;

        Matcher matcher = routeParam.matcher(currentRoute);

        while (matcher.find()) {
            String matchString = matcher.group(0);
            String argString = matchString.replace("${", "").replace("}", "");
            currentRoute = currentRoute.replace(matchString, args.get(argString).toString());
        }

        return address + currentRoute;
    }

    @Override
    public String getResource(Map<?, ?> arg) {
        return getFullAddress(arg);
    }
}
