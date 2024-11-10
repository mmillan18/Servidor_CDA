package Servidor_CDA.Servidor_CDA.repository;

import Servidor_CDA.Servidor_CDA.model.QR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QRRepository extends JpaRepository<QR, Long> {
    // Puedes agregar métodos personalizados aquí si los necesitas
}
