package com.ibm.fsd.mod.technology.config;

import com.ibm.fsd.mod.common.config.ModConifg;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = {ModConifg.class})
@ComponentScan("com.ibm.fsd")
public class AppConfig {
}
