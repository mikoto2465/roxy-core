package net.mikoto.roxy.core.model.network.server;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpServer implements Server {
    private String address;
}
