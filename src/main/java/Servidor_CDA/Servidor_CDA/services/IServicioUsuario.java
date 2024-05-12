package Servidor_CDA.Servidor_CDA.services;

import Servidor_CDA.Servidor_CDA.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface IServicioUsuario {

    Usuario addUsuario(Usuario usuario);
    Optional<Usuario> getUsuarioById(int id);
    List<Usuario> getAllUsuarios();
    Usuario updateUsuario(Usuario usuario, int id);
    void deleteUsuario(int id);
    boolean existeUsuarioConId(int id);
}
