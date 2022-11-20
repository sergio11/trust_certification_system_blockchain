package com.dreamsoftware.tcs.tasks;

import java.util.concurrent.Executor;

import com.mongodb.client.MongoClient;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.mongo.MongoLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
     * @param mongoClient
     * @return
     */
    @Bean
    public LockProvider lockProvider(final MongoClient mongoClient, @Value("${spring.data.mongodb.database}") final String databaseName) {
        return new MongoLockProvider(mongoClient.getDatabase(databaseName));
    }
}
