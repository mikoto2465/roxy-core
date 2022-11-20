package net.mikoto.roxy.core.model.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThreadPoolConfig {
    private boolean usingDefaultConfig = true;
    private int corePoolSize = 50;
    private int maximumPoolSize = 100;
    private int keepAliveTime = 1000;
    private TimeUnit timeUnit = MILLISECONDS;
    private BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
    private RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.AbortPolicy();
}
