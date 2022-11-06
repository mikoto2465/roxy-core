package net.mikoto.roxy.core.model.network.server;

import lombok.Getter;
import lombok.Setter;
import net.mikoto.roxy.core.model.network.weight.CurrentWeighted;

/**
 * @author mikoto
 * @date 2022/10/23
 * Create for core
 */
@Getter
@Setter
public class CurrentWeightedHttpServer extends WeightedHttpServer implements CurrentWeighted {
    private int currentWeight = 0;

    public CurrentWeightedHttpServer(String address, Integer weight) {
        super(address, weight);
    }
}
