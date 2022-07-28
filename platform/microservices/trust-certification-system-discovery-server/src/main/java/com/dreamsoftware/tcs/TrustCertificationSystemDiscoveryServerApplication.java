package com.dreamsoftware.tcs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class TrustCertificationSystemDiscoveryServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrustCertificationSystemDiscoveryServerApplication.class, args);
    }

}
