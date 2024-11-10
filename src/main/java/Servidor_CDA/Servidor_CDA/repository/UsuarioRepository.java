package Servidor_CDA.Servidor_CDA.repository;

import Servidor_CDA.Servidor_CDA.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    // Puedes agregar métodos personalizados aquí si los necesitas
}
