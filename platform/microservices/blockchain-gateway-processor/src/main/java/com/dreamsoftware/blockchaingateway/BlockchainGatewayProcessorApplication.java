package com.dreamsoftware.blockchaingateway;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@EnableEncryptableProperties
public class BlockchainGatewayProcessorApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlockchainGatewayProcessorApplication.class, args);
    }

}
