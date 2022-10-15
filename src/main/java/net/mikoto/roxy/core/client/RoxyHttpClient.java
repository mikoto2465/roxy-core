package net.mikoto.roxy.core.client;

import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.Header;
import com.dtflys.forest.annotation.Post;
import com.dtflys.forest.annotation.Var;

import java.util.Map;

/**
 * @author mikoto
 * @date 2022/10/15
 * Create for core
 */
public interface RoxyHttpClient {
    @Post(
            "${address}${route}"
    )
    void sendPostRequest(@Header Map<String, String> headerMap, @Var("address") String address, @Var("route") String route);

    @Get(
            "${address}${route}"
    )
    void sendGetRequest(@Header Map<String, String> headerMap, @Var("address") String address, @Var("route") String route);
}
