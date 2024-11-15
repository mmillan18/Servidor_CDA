package Servidor_CDA.Servidor_CDA.services;

import Servidor_CDA.Servidor_CDA.model.Empleado;
import Servidor_CDA.Servidor_CDA.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Empleado> getAllEmpleados() {
        return empleadoRepository.findAll();
    }

    public Optional<Empleado> getEmpleadoById(Long id) {
        return empleadoRepository.findById(id);
    }

    public Empleado createEmpleado(Empleado empleado) {
        // Encripta la contraseña antes de guardar
        String encryptedPassword = passwordEncoder.encode(empleado.getPassword());
        empleado.setPassword(encryptedPassword);
        return empleadoRepository.save(empleado);
    }
    public Empleado updateEmpleado(Long id, Empleado empleadoDetails) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        empleado.setNombre(empleadoDetails.getNombre());
        empleado.setUsername(empleadoDetails.getUsername());
        empleado.setPassword(empleadoDetails.getPassword());

        return empleadoRepository.save(empleado);
    }

    public void deleteEmpleado(Long id) {
        empleadoRepository.deleteById(id);
    }
}
