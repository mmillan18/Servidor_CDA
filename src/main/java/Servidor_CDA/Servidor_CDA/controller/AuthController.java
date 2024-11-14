package Servidor_CDA.Servidor_CDA.controller;

import Servidor_CDA.Servidor_CDA.model.Empleado;
import Servidor_CDA.Servidor_CDA.services.CustomUserDetails;
import Servidor_CDA.Servidor_CDA.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/CDA")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        // Autenticar al usuario con las credenciales proporcionadas
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        // Establecer el contexto de seguridad
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Obtener detalles del usuario autenticado y hacer el casteo
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Empleado empleado = customUserDetails.getEmpleado();

        // Crear la respuesta con el rol del usuario
        LoginResponse response = new LoginResponse(empleado.getUsername(), empleado.getRol());
        return ResponseEntity.ok(response);
    }

    // Clase interna para las credenciales de login
    public static class LoginRequest {
        private String username;
        private String password;

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
