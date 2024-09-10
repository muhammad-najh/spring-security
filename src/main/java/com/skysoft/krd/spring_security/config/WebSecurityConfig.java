package com.skysoft.krd.spring_security.config;

import com.skysoft.krd.spring_security.filters.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // we are telling spring security we
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtAuthFilter JwtAuthFilter;
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, JwtAuthFilter jwtAuthFilter) throws Exception {
        httpSecurity
                .authorizeHttpRequests(auth->{
                    auth.requestMatchers("/posts","/error","/public/**","/auth/**").permitAll();
//                    auth.requestMatchers("/posts/**").hasRole("ADMIN");
                    auth.anyRequest().authenticated(); //all should go to security filter chain
                })
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionConfig->
                        sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//              .formLogin(Customizer.withDefaults());
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);


        return httpSecurity.build();
    }



    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}































//    @Bean
//    UserDetailsService inMemoryUserDetailsService() {
//        UserDetails normalUser= User
//                .withUsername("Ali")
//                .password(passwordEncoder().encode("Ali@123"))
//                .roles("USER")
//                .build();
//        UserDetails adminUser=User
//                .withUsername("MOHAMMED")
//                .roles("ADMIN")
//                .password(passwordEncoder().encode("Hama@123"))
//                .build();
//
//        return new InMemoryUserDetailsManager(normalUser, adminUser);
//    }