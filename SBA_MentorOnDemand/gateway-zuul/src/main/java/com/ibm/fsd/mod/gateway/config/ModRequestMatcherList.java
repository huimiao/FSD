package com.ibm.fsd.mod.gateway.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ModRequestMatcherList {
    private List<RequestMatcher> mentor = new ArrayList<>();
    private List<RequestMatcher> user = new ArrayList<>();
    private List<RequestMatcher> all = new ArrayList<>();
}
