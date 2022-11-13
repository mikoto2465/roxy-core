package net.mikoto.roxy.core.model.task;

import net.mikoto.roxy.core.algorithm.Algorithm;
import net.mikoto.roxy.core.model.AbstractHasAHashMapClass;

import java.util.Map;

public class AlgorithmTask extends AbstractHasAHashMapClass<Algorithm<?>> implements Task {

    @Override
    public Map<Object, Object> assign(Map<Object, Object> inputValue) {
        for (Map.Entry<String, Algorithm<?>> entry :
                super.dataMap.entrySet()) {
            inputValue.put(entry.getKey(), entry.getValue().run());
        }
        return inputValue;
    }
}
