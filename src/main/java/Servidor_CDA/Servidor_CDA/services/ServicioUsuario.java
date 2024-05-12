package Servidor_CDA.Servidor_CDA.services;

import Servidor_CDA.Servidor_CDA.model.Usuario;
import Servidor_CDA.Servidor_CDA.model.Vehiculo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioUsuario implements IServicioUsuario{

    private final List<Usuario> usuarios = new ArrayList<>();

    @Override
    public Usuario addUsuario(Usuario usuario) {
        if (existeUsuarioConId(usuario.getCedula())) {
            throw new IllegalArgumentException("Un usuario con el mismo ID ya existe");
        }
        usuarios.add(usuario);
        return usuario;
    }

    @Override
    public Optional<Usuario> getUsuarioById(int id) {
        return usuarios.stream()
                .filter(usuario -> usuario.getCedula() == id)
                .findFirst();
    }

    @Override
    public List<Usuario> getAllUsuarios() {
        return usuarios;
    }

    @Override
    public Usuario updateUsuario( Usuario usuario,int id) {
        // Intentar encontrar el usuario existente con el ID dado
        Optional<Usuario> existingUsuarioOpt = getUsuarioById(id);

        if (!existingUsuarioOpt.isPresent()) {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }

        Usuario existingUsuario = existingUsuarioOpt.get();

        // Preservar la lista original de vehículos antes de actualizar
        List<Vehiculo> originalVehiculos = new ArrayList<>(existingUsuario.getVehiculos());

        // Actualizar los atributos del usuario
        existingUsuario.setNombre(usuario.getNombre());
        existingUsuario.setCorreo(usuario.getCorreo());
        // Otros campos pueden ser actualizados de la misma manera

        // Reestablecer la lista original de vehículos
        existingUsuario.setVehiculos(originalVehiculos);

        return existingUsuario;
    }

    @Override
    public void deleteUsuario(int id) {
        usuarios.removeIf(u -> u.getCedula() == id);
    }

    @Override
    public boolean existeUsuarioConId(int id) {
        return usuarios.stream().anyMatch(usuario -> usuario.getCedula() == id);
    }
}
