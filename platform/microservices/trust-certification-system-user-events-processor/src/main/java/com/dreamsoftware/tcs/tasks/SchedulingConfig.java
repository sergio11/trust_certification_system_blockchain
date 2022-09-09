package com.dreamsoftware.tcs.tasks;

import java.util.concurrent.Executor;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.redis.spring.RedisLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 *
 * @author ssanchez
 */
@Configuration
@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "10m")
public class SchedulingConfig implements SchedulingConfigurer {

    private final int POOL_SIZE = 10;

    /**
     *
     * @param scheduledTaskRegistrar
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(taskExecutor());
    }

    /**
     *
     * @return
     */
    @Bean(destroyMethod = "shutdown")
    public Executor taskExecutor() {
        final ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(POOL_SIZE);
        threadPoolTaskScheduler.setThreadNamePrefix("task-pool-thread");
        return threadPoolTaskScheduler;
    }

    /**
     *
     * @param connectionFactory
     * @return
     */
    @Bean
    public LockProvider lockProvider(final RedisConnectionFactory connectionFactory) {
        return new RedisLockProvider(connectionFactory, "Shedlock");
    }
}
