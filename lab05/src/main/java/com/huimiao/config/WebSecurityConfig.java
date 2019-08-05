package com.huimiao.config;

import com.huimiao.controller.Http401UnauthorizedEntryPoint;
import com.huimiao.handler.JsonLoginSuccessHandler;
import com.huimiao.handler.JwtTokenRefreshHandler;
import com.huimiao.service.JwtAuthenticationProvider;
import com.huimiao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserService userService;

    @Autowired
    private Http401UnauthorizedEntryPoint authenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/register", "/login","/logoff", "/static/**", "/favicon.ico", "/captcha", "/regist","/").permitAll()
                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated()
                .and()
                .apply(new JsonLoginConfigurer<>()).loginSuccessHandler(jsonLoginSuccessHandler())
                .and()
                .apply(new JwtLoginConfigurer<>()).setSuccessHandler(new JwtTokenRefreshHandler())
                .and()
                .httpBasic().disable()
                .formLogin().disable()
                .csrf().disable();
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        /*return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encodedPassword.equals(rawPassword.toString());
            }
        };*/
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.eraseCredentials(false).authenticationProvider(jwtAuthenticationProvider()).jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select username, password, enabled from users where username = ?")
                .authoritiesByUsernameQuery("select username, role from users u, user_role ur, roles r where u.username =? and u.id =ur.uid = ur.rid = r.id")
                .groupAuthoritiesByUsername("select r.id, r.role, r.role from roles r, user_role ur, users u where u.username=? and u.id = ur.uid and ur.rid = r.id");
    }

    @Bean
    protected JsonLoginSuccessHandler jsonLoginSuccessHandler() {
        return new JsonLoginSuccessHandler(userService);
    }

    @Bean("jwtAuthenticationProvider")
    protected AuthenticationProvider jwtAuthenticationProvider() {
        return new JwtAuthenticationProvider(userService);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    @Bean(name = "userDetailsService")
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }
}
