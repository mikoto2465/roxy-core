package net.mikoto.roxy.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SourceConfig {
    private String algorithmInterfaceName;
    private String algorithmImplName;
    private InstantiableObject[] algorithmImplParams;
}
