package net.mikoto.roxy.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.mikoto.roxy.core.algorithm.Algorithm;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resource {
    private String resourceName;
    private Algorithm<?> resourceAlgorithm;
}
