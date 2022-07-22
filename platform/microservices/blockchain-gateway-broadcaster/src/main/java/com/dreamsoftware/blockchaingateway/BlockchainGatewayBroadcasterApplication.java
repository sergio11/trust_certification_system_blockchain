package com.dreamsoftware.blockchaingateway;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableEncryptableProperties
public class BlockchainGatewayBroadcasterApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlockchainGatewayBroadcasterApplication.class, args);
    }

}
