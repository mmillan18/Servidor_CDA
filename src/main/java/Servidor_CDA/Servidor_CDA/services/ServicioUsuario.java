package Servidor_CDA.Servidor_CDA.services;

import Servidor_CDA.Servidor_CDA.model.Usuario;
import Servidor_CDA.Servidor_CDA.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioUsuario implements IServicioUsuario {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario addUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Optional<Usuario> getUsuarioById(int id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }


    @Override
    @Transactional
    public Usuario updateUsuario(Usuario usuario, int id) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);

        if (usuarioExistente.isPresent()) {
            Usuario existingUsuario = usuarioExistente.get();
            existingUsuario.setNombre(usuario.getNombre());
            existingUsuario.setCorreo(usuario.getCorreo());

            // Actualizar la colección de vehiculos sin sobrescribir la referencia
            existingUsuario.getVehiculos().clear();
            existingUsuario.getVehiculos().addAll(usuario.getVehiculos());

            return usuarioRepository.save(existingUsuario);
        } else {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }
    }


    @Override
    @Transactional
    public void deleteUsuario(int id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    @Override
    public boolean existeUsuarioConId(int id) {
        return usuarioRepository.existsById(id);
    }
}
