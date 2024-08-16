package com.skysoft.krd.spring_security.config;

import org.apache.catalina.util.SessionConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // we are telling spring security we
public class WebSecurityConfig {




    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(auth->{
                    auth.requestMatchers("/posts","/error","/public/**").permitAll();
                    auth.requestMatchers("/posts/**").hasRole("ADMIN");
                    auth.anyRequest().authenticated();
                })
                .csrf(csrfConfig->csrfConfig.disable())
                .sessionManagement(sessionConfig->
                        sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//                .formLogin(Customizer.withDefaults());


        return httpSecurity.build();
    }

    @Bean
    UserDetailsService inMemoryUserDetailsService() {
        UserDetails normalUser= User
                .withUsername("Ali")
                .password(passwordEncoder().encode("Ali@123"))
                .roles("USER")
                .build();
        UserDetails adminUser=User
                .withUsername("MOHAMMED")
                .roles("ADMIN")
                .password(passwordEncoder().encode("Hama@123"))
                .build();

        return new InMemoryUserDetailsManager(normalUser, adminUser);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
