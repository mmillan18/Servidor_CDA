package Servidor_CDA.Servidor_CDA.repository;

import Servidor_CDA.Servidor_CDA.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, String> {
    boolean existsByPlacaAndUsuarioCedula(String placa, int cedula);
    Optional<Vehiculo> findByPlacaAndUsuarioCedula(String placa, int cedula);
    List<Vehiculo> findByUsuarioCedula(int usuarioCedula);

}
