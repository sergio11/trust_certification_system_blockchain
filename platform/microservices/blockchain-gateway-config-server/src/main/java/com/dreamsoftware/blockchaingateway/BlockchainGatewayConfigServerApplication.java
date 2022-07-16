package com.dreamsoftware.blockchaingateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class BlockchainGatewayConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlockchainGatewayConfigServerApplication.class, args);
    }

}
