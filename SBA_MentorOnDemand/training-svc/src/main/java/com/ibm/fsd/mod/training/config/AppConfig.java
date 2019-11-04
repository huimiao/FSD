package com.ibm.fsd.mod.training.config;

import com.ibm.fsd.mod.common.config.ModConifg;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(ModConifg.class)
public class AppConfig {
}
