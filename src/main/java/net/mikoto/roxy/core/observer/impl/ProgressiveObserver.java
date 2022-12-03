package net.mikoto.roxy.core.observer.impl;

import net.mikoto.roxy.core.observer.Observer;
import org.apache.logging.log4j.Logger;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class ProgressiveObserver extends Observer {
    private final Integer total;
    private Integer count = 0;
    private static final NumberFormat numberFormat = new DecimalFormat("0.0");

    public ProgressiveObserver(Integer total) {
        this.total = total;
    }

    @Override
    public void doAfterUpdate(Object target) {
        count++;
    }

    public Integer getTotal() {
        return total;
    }

    public Integer getCount() {
        return count;
    }

    public String getPercent() {
        String percent = numberFormat.format(getCount() * 100 / getTotal()) + "%";
        super.getLogger().info("Observer -> " + percent);
        return percent;
    }
}
