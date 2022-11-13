package net.mikoto.roxy.core.model.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceConfig {
    private int weight;
    private String resourceName;
    private String algorithmName;
    private InstantiableObject[] algorithmParams;
}
