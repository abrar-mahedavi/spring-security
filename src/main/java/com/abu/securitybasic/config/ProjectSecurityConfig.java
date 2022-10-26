package com.abu.securitybasic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

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

        /**
         * Configuration to permit all the requests
         */
		/*
		http.authorizeHttpRequests()
                .anyRequest().permitAll()
				.and().httpBasic()
                .and().formLogin();
        return http.build();
        */


    }

    /*
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        /**
         * Approach 1 where we use withDefaultPasswordEncoder() method
         * while creating the user details
         */
        /*
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("12345")
                .authorities("admin")
                .build();
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("12345")
                .authorities("read")
                .build();
        return new InMemoryUserDetailsManager(admin, user);
         */

    /**
     * Approach 2 where we don't define password encoder while creating the user details. Instead, a separate
     * PasswordEncoder bean will be created.
     */
        /*
        UserDetails admin = User.withUsername("admin")
                .password("12345")
                .authorities("admin")
                .build();
        UserDetails user = User.withUsername("user")
                .password("12345")
                .authorities("read")
                .build();
        return new InMemoryUserDetailsManager(admin, user);
         */
//    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
