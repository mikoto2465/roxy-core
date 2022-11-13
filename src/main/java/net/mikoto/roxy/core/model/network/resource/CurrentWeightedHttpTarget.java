package net.mikoto.roxy.core.model.network.resource;

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
public class CurrentWeightedHttpTarget extends WeightedHttpTarget implements CurrentWeighted {
    private int currentWeight = 0;

    public CurrentWeightedHttpTarget(String address, String route, Integer weight) {
        super(address, route, weight);
    }
}
