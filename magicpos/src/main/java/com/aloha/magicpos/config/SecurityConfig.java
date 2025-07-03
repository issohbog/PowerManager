package com.aloha.magicpos.config; // ← 네 패키지 경로에 맞게 수정!

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // CSRF 끄기
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // 🔥 모든 요청 허용
            )
            .formLogin(login -> login.disable()) // 로그인 폼 비활성화
            .httpBasic(basic -> basic.disable()); // 기본 인증도 비활성화

        return http.build();
    }
}
