package com.mailnaxx;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // ログイン設定
        http.formLogin(login -> login
                // 入力値送信先URL
                .loginProcessingUrl("/login")
                // ログイン画面のURL
                .loginPage("/login")
                // ログイン成功後のリダイレクト先URL
                .defaultSuccessUrl("/top")
                // ログイン成功後のリダイレクト先URL
                .failureUrl("/login?error")
                .permitAll()
        // ログアウト設定
        ).logout(logout -> logout
                // ログアウト成功後の遷移先（仮）
                .logoutSuccessUrl("/login")
        // URLごとの認可設定
        ).authorizeHttpRequests(authz -> authz
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                // ログインなしでもアクセス可
                // .mvcMatchers("/").permitAll()
                // ROLE_GENERALのみアクセス可
                .mvcMatchers("/general").hasRole("GENERAL")
                // ROLE_ADMINのみアクセス可
                .mvcMatchers("/admin").hasRole("ADMIN")
                // 他のURLはログイン後のみ
                .anyRequest().authenticated()
        );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
