package com.ibm.fsd.mod.tech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableDiscoveryClient
public class TechnologyApplication {

    public static void main(String[] args) {
        SpringApplication.run(TechnologyApplication.class, args);
    }
    @GetMapping("/")
    public String test(){
        return "hello technology";
    }
}
