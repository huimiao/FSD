package com.ibm.fsd.mod.account.config;

import com.ibm.fsd.mod.common.config.ModConifg;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@Configuration
@Import(value = {ModConifg.class})
@ComponentScan("com.ibm.fsd")
public class AppConfig {
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor(){
        return new MethodValidationPostProcessor();
    }
}
