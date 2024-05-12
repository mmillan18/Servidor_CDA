package Servidor_CDA.Servidor_CDA.services;

import Servidor_CDA.Servidor_CDA.model.Vehiculo;

import java.util.List;
import java.util.Optional;

public interface IServicioVehiculo {

    // Método para registrar un vehículo, necesita el ID del usuario
    Vehiculo saveVehiculo(Vehiculo vehiculo, int usuarioId);

    // Método para obtener un vehículo por su placa
    Optional<Vehiculo> getVehiculoByPlaca(String placa);

    // Método para obtener todos los vehículos
    List<Vehiculo> getAllVehiculos();

    // Método para actualizar un vehículo, necesita el ID del usuario
    Vehiculo updateVehiculo(Vehiculo vehiculo, int usuarioId, String placa);

    // Método para eliminar un vehículo por su placa, necesita el ID del usuario
    void deleteVehiculo(String placa, int usuarioId);

}
