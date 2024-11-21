package Servidor_CDA.Servidor_CDA.controller;

import Servidor_CDA.Servidor_CDA.model.Empleado;
import Servidor_CDA.Servidor_CDA.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/CDA")
public class AuthController {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        System.out.println("Intentando autenticar usuario: " + loginRequest.getUsername());

        Optional<Empleado> optionalEmpleado = empleadoRepository.findByUsername(loginRequest.getUsername());

        if (optionalEmpleado.isEmpty()) {
            System.out.println("Usuario no encontrado.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado.");
        }

        Empleado empleado = optionalEmpleado.get();

        if (!passwordEncoder.matches(loginRequest.getPassword(), empleado.getPassword())) {
            System.out.println("Contraseña incorrecta.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta.");
        }

        System.out.println("Autenticación exitosa.");
        String role = empleado.getRol();

        return ResponseEntity.ok(new LoginResponse(
                empleado.getUsername(),
                role
        ));
    }

    public static class LoginRequest {
        private String username;
        private String password;

        // Getters y Setters
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
    }

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
