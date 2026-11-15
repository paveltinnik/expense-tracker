package com.example.demo.security.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder(12)

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests { auth ->
                auth
                    // Регистрация — публичная
                    .requestMatchers(HttpMethod.POST, "/api/v1/auth/register").permitAll()

                    // Actuator health — для мониторинга и Docker healthcheck
                    .requestMatchers("/actuator/health", "/actuator/health/**").permitAll()

                    // Всё остальное — требует аутентификации
                    .anyRequest().authenticated()
            }
            .httpBasic { }
        return http.build()
    }
}