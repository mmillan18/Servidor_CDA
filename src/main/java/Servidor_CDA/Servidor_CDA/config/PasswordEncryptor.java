package Servidor_CDA.Servidor_CDA.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncryptor {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // Generar el hash de la contraseña "admin123"
        String hashedPassword = encoder.encode("admin123");
        System.out.println("Admin password: " + hashedPassword);
    }
}
