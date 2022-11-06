package net.mikoto.roxy.core.model.network.server;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.mikoto.roxy.core.model.network.weight.Weighted;

/**
 * @author mikoto
 * @date 2022/10/23
 * Create for core
 */
public class WeightedHttpServer extends HttpServer implements Weighted {
    private final int weight;

    public WeightedHttpServer(Integer weight) {
        this.weight = weight;
    }

    public WeightedHttpServer(String address, Integer weight) {
        super(address);
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
