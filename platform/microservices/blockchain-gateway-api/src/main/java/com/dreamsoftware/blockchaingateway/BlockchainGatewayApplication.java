package com.dreamsoftware.blockchaingateway;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@OpenAPIDefinition(info
        = @Info(
                title = "Certification Authority API",
                version = "1.0",
                description = "Trust Certification System Blockchain - Certification Authority API API v1.0"
        )
)
@EnableEncryptableProperties
public class BlockchainGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlockchainGatewayApplication.class, args);
    }

}
