package com.jtarcio.closetrental.shared.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Swagger e docs públicos
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()

                        // Catálogo
                        .requestMatchers(HttpMethod.GET, "/v1/catalogo/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/v1/catalogo/**").hasAnyRole("ADMIN", "ATENDENTE")
                        .requestMatchers(HttpMethod.PUT, "/v1/catalogo/**").hasAnyRole("ADMIN", "ATENDENTE")
                        .requestMatchers(HttpMethod.DELETE, "/v1/catalogo/**").hasRole("ADMIN")

                        // Clientes
                        .requestMatchers("/v1/clientes/**").hasAnyRole("ADMIN", "ATENDENTE")

                        // Locações
                        .requestMatchers("/v1/locacoes/**").hasAnyRole("ADMIN", "ATENDENTE", "CLIENTE")

                        // Estoque
                        .requestMatchers("/v1/estoque/**").hasAnyRole("ADMIN", "ATENDENTE")

                        // Pagamentos
                        .requestMatchers("/v1/pagamentos/**").hasAnyRole("ADMIN", "ATENDENTE", "CLIENTE")

                        // Qualquer outra requisição precisa autenticação
                        .anyRequest().authenticated()
                )
                .httpBasic(basic -> {});

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin123"))
                .roles("ADMIN")
                .build();

        UserDetails atendente = User.builder()
                .username("atendente")
                .password(passwordEncoder().encode("atendente123"))
                .roles("ATENDENTE")
                .build();

        UserDetails cliente = User.builder()
                .username("cliente")
                .password(passwordEncoder().encode("cliente123"))
                .roles("CLIENTE")
                .build();

        return new InMemoryUserDetailsManager(admin, atendente, cliente);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
