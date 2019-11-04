package com.ibm.fsd.mod.technology;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TechnologyApplication {

    public static void main(String[] args) {
        SpringApplication.run(TechnologyApplication.class, args);
    }

}
