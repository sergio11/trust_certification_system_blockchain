package com.dreamsoftware.blockchaingateway;

import net.consensys.eventeum.annotation.EnableEventeum;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEventeum
public class BlockchainGatewayBroadcasterApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlockchainGatewayBroadcasterApplication.class, args);
    }

}
