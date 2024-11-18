package Servidor_CDA.Servidor_CDA.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/CDA")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        // Determinar el rol basado en el username
        String role;
        if ("admin".equalsIgnoreCase(loginRequest.getUsername())) {
            role = "ADMIN"; // Asignar ADMIN si el username es 'admin'
        } else {
            role = "EMPLOYEE"; // Por defecto, todos los demás usuarios serán EMPLOYEE
        }

        // Retornar la respuesta con el username y el rol determinado
        return ResponseEntity.ok(new LoginResponse(
                loginRequest.getUsername(),
                role
        ));
    }


    // Clase interna para las credenciales de login
    public static class LoginRequest {
        private String username;
        private String password;

        private String rol; // Campo adicional para el rol

        // Getters y setters
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getRol() {
            return rol;
        }

        public void setRol(String rol) {
            this.rol = rol;
        }
    }

    // Clase interna para la respuesta de login
    public static class LoginResponse {
        private String username;
        private String role;

        public LoginResponse(String username, String role) {
            this.username = username;
            this.role = role;
        }

        // Getters
        public String getUsername() {
            return username;
        }

        public String getRole() {
            return role;
        }
    }
}
