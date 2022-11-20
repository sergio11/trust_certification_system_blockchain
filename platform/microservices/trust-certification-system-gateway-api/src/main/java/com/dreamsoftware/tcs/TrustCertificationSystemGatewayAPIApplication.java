package com.dreamsoftware.tcs;

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
                title = "Trust Certification System Blockchain API",
                version = "1.0",
                description = "Trust Certification System Blockchain - Certification API v1.0"
        )
)
@EnableEncryptableProperties
public class TrustCertificationSystemGatewayAPIApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrustCertificationSystemGatewayAPIApplication.class, args);
    }

}
