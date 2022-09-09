package com.dreamsoftware.tcs.config;

import com.redis.om.spring.annotations.EnableRedisDocumentRepositories;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author ssanchez
 */
@Configuration
@EnableRedisDocumentRepositories(basePackages = "com.dreamsoftware.tcs.persistence.redis.entity.repository.*")
@Slf4j
public class RedisConfig {

}
