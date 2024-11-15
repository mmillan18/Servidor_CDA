package Servidor_CDA.Servidor_CDA.repository;

import Servidor_CDA.Servidor_CDA.model.QR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface QRRepository extends JpaRepository<QR, Long> {
    List<QR> findByFechaCreacionBetween(Date fechaInicio, Date fechaFin);
    // Puedes agregar métodos personalizados aquí si los necesitas
}
