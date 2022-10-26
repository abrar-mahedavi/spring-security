package com.abu.securitybasic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * The type Project security config.
 */
@Configuration
public class ProjectSecurityConfig {

    /**
     * Default security filter chain security filter chain.
     *
     * @param http the http
     * @return the security filter chain
     * @throws Exception the exception
     */
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        /**
         * Default configurations which will secure all the requests
         */
        /*
        http.authorizeRequests().anyRequest().authenticated();
        http.formLogin();
        http.httpBasic();
        return http.build();
        */

        /**
         * Custom configurations as per our requirement
         */

        http.authorizeHttpRequests()
                .antMatchers("/api/v1/my-account", "/api/v1/my-balance", "/api/v1/my-loans", "/api/v1/my-cards")
                .authenticated()
                .antMatchers("/notices", "/contact")
                .permitAll()
                .and().httpBasic()
                .and().formLogin();
        return http.build();

        /**
         * Configuration to deny all the requests
         */
        /*
        http.authorizeHttpRequests()
                .anyRequest().denyAll()
                .and().httpBasic()
                .and().formLogin();
        return http.build();
        */

    }

}
