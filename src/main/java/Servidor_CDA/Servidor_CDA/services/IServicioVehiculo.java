package Servidor_CDA.Servidor_CDA.services;

import Servidor_CDA.Servidor_CDA.model.Vehiculo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IServicioVehiculo {

    // Método para registrar un vehículo, necesita el ID del usuario
    Vehiculo saveVehiculoWithImage(Vehiculo vehiculo, int usuarioId, MultipartFile imageFile) throws IOException;

    // Método para obtener un vehículo por su placa
    Optional<Vehiculo> getVehiculoByPlaca(String placa);

    List<Vehiculo> getVehiculosByUsuario(int usuarioId);

    // Método para obtener todos los vehículos
    List<Vehiculo> getAllVehiculos();

    // Método para actualizar un vehículo, necesita el ID del usuario
    Vehiculo updateVehiculo(Vehiculo vehiculo, int usuarioId, String placa);

    // Método para eliminar un vehículo por su placa, necesita el ID del usuario
    void deleteVehiculo(String placa, int usuarioId);

}
