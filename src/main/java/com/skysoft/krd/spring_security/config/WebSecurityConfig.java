package com.skysoft.krd.spring_security.config;

import com.skysoft.krd.spring_security.enums.Role;
import com.skysoft.krd.spring_security.filters.JwtAuthFilter;
import com.skysoft.krd.spring_security.handlers.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.skysoft.krd.spring_security.enums.Permission.POST_UPDATE;

@Configuration
@EnableWebSecurity // we are telling spring security we
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;
    private final OAuth2SuccessHandler auth2SuccessHandler;

    private static final String[] publicRoutes = {
            "/error", "/auth/**", "/home.html"
    };

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(publicRoutes).permitAll()
                        .requestMatchers("/posts/**").authenticated()
                        .anyRequest().authenticated())
                .csrf(csrfConfig -> csrfConfig.disable())
                .sessionManagement(sessionConfig -> sessionConfig
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth2Config -> oauth2Config
                        .failureUrl("/login?error=true")
                        .successHandler(auth2SuccessHandler)
                );
//                .formLogin(Customizer.withDefaults());

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