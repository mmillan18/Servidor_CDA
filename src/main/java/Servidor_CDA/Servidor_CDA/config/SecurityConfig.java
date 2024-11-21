package Servidor_CDA.Servidor_CDA.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .csrf(csrf -> csrf.disable()) // Desactiva CSRF
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfig = new org.springframework.web.cors.CorsConfiguration();
                    corsConfig.addAllowedOrigin("http://localhost:4200"); // Permitir solo Angular
                    corsConfig.addAllowedMethod("*"); // Métodos permitidos
                    corsConfig.addAllowedHeader("*"); // Permitir todos los headers
                    corsConfig.setAllowCredentials(true); // Permitir credenciales
                    return corsConfig;
                }))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/CDA/login").permitAll() // Permitir login
                        .requestMatchers("/CDA/empleados/**").permitAll()
                        .requestMatchers("/CDA/qr/**").permitAll()
                        .requestMatchers("/CDA/revisiones/**").permitAll()
                        .requestMatchers("/CDA/usuarios/**").permitAll()
                        .requestMatchers("/CDA/vehiculos/**").permitAll()
                        .anyRequest().authenticated() // Resto requiere autenticación
                )
                .httpBasic(httpBasic -> {}); // Configurar autenticación básica
        return http.build();
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
