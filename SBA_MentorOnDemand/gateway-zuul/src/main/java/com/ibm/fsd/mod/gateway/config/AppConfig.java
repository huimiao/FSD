package com.ibm.fsd.mod.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Configuration
@EnableConfigurationProperties(FilerProperties.class)
public class AppConfig {
    @Autowired
    private FilerProperties filerProperties;

    @Bean
    @ConditionalOnMissingBean(ModRequestMatcherList.class)
    public ModRequestMatcherList modRequestMatcherList() {
        ModRequestMatcherList matcherList = new ModRequestMatcherList();

        List<RequestMatcher> t;
        t = filerProperties.getMentor().stream()
                .map(m -> new AntPathRequestMatcher(m.getUri(), m.getMethod()))
                .collect(toList());

        if (t != null && t.size() != 0) {
            matcherList.getMentor().addAll(t);
        }

        t = filerProperties.getUser().stream()
                .map(m -> new AntPathRequestMatcher(m.getUri(), m.getMethod()))
                .collect(toList());

        if (t != null && t.size() != 0) {
            matcherList.getUser().addAll(t);
        }

        matcherList.getAll().addAll(matcherList.getUser());
        matcherList.getAll().addAll(matcherList.getMentor());
        return matcherList;
    }

}
