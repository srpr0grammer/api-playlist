package com.apiplaylist.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    SecurityFilter securityFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(new AntPathRequestMatcher("/auth/login", HttpMethod.POST.toString())).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/auth/register", HttpMethod.POST.toString())).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/music", HttpMethod.POST.toString())).authenticated()
                        .requestMatchers(new AntPathRequestMatcher("/api/music/**", HttpMethod.GET.toString())).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/music", HttpMethod.DELETE.toString())).authenticated()
                        .requestMatchers(new AntPathRequestMatcher("/api/playlist", HttpMethod.POST.toString())).authenticated()
                        .requestMatchers(new AntPathRequestMatcher("/api/playlist/**", HttpMethod.GET.toString())).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/playlist", HttpMethod.DELETE.toString())).authenticated()

                        .anyRequest().permitAll()
                )
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
