package net.mikoto.roxy.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.mikoto.roxy.core.model.network.weight.CurrentWeighted;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resource implements CurrentWeighted {
    public Resource(int weight, String resourceName, Strategy<?> resourceStrategy) {
        this.weight = weight;
        this.resourceName = resourceName;
        this.resourceStrategy = resourceStrategy;
    }

    private int weight;
    private int currentWeight = 0;
    private String resourceName;
    private Strategy<?> resourceStrategy;
}
