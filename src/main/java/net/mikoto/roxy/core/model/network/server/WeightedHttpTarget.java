package net.mikoto.roxy.core.model.network.server;

import net.mikoto.roxy.core.model.network.weight.Weighted;

/**
 * @author mikoto
 * @date 2022/10/23
 * Create for core
 */
public class WeightedHttpTarget extends HttpTarget implements Weighted {
    private final int weight;

    public WeightedHttpTarget(Integer weight) {
        this.weight = weight;
    }

    public WeightedHttpTarget(String address, String route , Integer weight) {
        super(address, route);
        this.weight = weight;
    }

    /**
     * Get the weight of the model.
     *
     * @return The weight.
     */
    @Override
    public int getWeight() {
        return weight;
    }
}
