package Servidor_CDA.Servidor_CDA.repository;

import Servidor_CDA.Servidor_CDA.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
