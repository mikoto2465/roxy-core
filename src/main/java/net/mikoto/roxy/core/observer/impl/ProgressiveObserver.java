package net.mikoto.roxy.core.observer.impl;

import net.mikoto.roxy.core.observer.Observer;
import org.apache.logging.log4j.Logger;

import java.text.DecimalFormat;

public class ProgressiveObserver<T> extends Observer<T> {
    private final Integer total;
    private Integer count = 0;
    private static final DecimalFormat decimalFormat = new DecimalFormat("##.0000%");

    public ProgressiveObserver(Integer total) {
        this.total = total;
    }

    @Override
    public void doAfterUpdate(T target) {
        count++;
    }

    public Integer getTotal() {
        return total;
    }

    public Integer getCount() {
        return count;
    }

    public String getPercent() {
        String percent = decimalFormat.format(getCount() / getTotal());
        super.getLogger().info("Observer " + super.getName() + " -> " + percent);
        return percent;
    }
}
