package com.dreamsoftware.tcs;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@EnableEncryptableProperties
public class TrustCertificationSystemProcessorApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrustCertificationSystemProcessorApplication.class, args);
    }

}
