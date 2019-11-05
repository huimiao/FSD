package com.ibm.fsd.mod.gateway.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ConfigurationProperties("mod.filter")
public class FilerProperties {
    @NestedConfigurationProperty
    List<RequestInfo> user = new ArrayList<>();
    @NestedConfigurationProperty
    List<RequestInfo> mentor = new ArrayList<>();
}
