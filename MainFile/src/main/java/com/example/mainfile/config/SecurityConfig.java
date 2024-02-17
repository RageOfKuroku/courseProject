package com.example.mainfile.config;

import com.example.mainfile.filter.TokenGenFilter;
import com.example.mainfile.filter.TokenValidFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    @Autowired
    private TokenGenFilter genFilter;
    @Autowired
    private TokenValidFilter validFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable);

        http
                .authorizeRequests(auth -> auth
                        .requestMatchers("/registration/**").permitAll()
                        .requestMatchers("/hotels").permitAll()
                        .requestMatchers("/user/login").permitAll()
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/user/**").hasAnyAuthority("USER", "ADMIN"));


        http.formLogin(cust -> {
                    cust.loginPage("/user/login");
                    cust.usernameParameter("emailLogin");
                    cust.passwordParameter("password");
                    cust.successHandler((request, response, authentication) -> {
                        String email = request.getParameter("emailLogin");
                        String password = request.getParameter("password");
                        response.sendRedirect("/admin");
                    });
                    cust.failureHandler((request, response, authenticationException) -> {
                        String email = request.getParameter("emailLogin");
                        String password = request.getParameter("password");
                        response.sendRedirect("/user/login");
                    });
                });

        http.addFilterBefore(validFilter, SecurityContextHolderAwareRequestFilter.class);
        http.addFilterAfter(genFilter, LogoutFilter.class);


        return http.build();
    }
}





