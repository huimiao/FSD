package com.huimiao.lab06.config;

import com.huimiao.lab06.filter.CaptchaFilter;
import com.huimiao.lab06.handler.CaptchaValidationFailureHandler;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.logout.LogoutFilter;

public class CaptchaFilterConfigurer<T extends CaptchaFilterConfigurer<T, B>, B extends HttpSecurityBuilder<B>> extends AbstractHttpConfigurer<T, B> {
    private CaptchaFilter captchaFilter;

    public CaptchaFilterConfigurer() {
        this.captchaFilter = new CaptchaFilter();
    }

    @Override
    public void configure(B builder) {
        captchaFilter.setFailureHandler(new CaptchaValidationFailureHandler());
        CaptchaFilter filter = postProcess(captchaFilter);

        builder.addFilterAfter(filter, LogoutFilter.class);
    }
}
