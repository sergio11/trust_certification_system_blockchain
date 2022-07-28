package com.dreamsoftware.tcs;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableEncryptableProperties
public class TrustCertificationSystemMonitorServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrustCertificationSystemMonitorServerApplication.class, args);
    }

}
