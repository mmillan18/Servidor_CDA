package Servidor_CDA.Servidor_CDA.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordTest {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // Contraseña en texto plano
        String rawPassword = "admin123";

        // Hash almacenado en la base de datos
        String storedHash = "$2a$10$26//uOVaa/5qltYfSLFAcu57FxQ8sRuC4Te2Dpz7fcgV6TeFw.Pjq";

        // Verificar si coinciden
        boolean matches = encoder.matches(rawPassword, storedHash);

        System.out.println("¿Las contraseñas coinciden? " + matches);
    }
}
