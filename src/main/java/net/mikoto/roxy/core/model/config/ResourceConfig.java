package net.mikoto.roxy.core.model.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.mikoto.yukino.model.config.InstantiableObject;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceConfig {
    private int weight;
    private String resourceName;
    private InstantiableObject resourceStrategy;
}
