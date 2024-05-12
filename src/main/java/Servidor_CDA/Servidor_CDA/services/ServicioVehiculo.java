package Servidor_CDA.Servidor_CDA.services;

import Servidor_CDA.Servidor_CDA.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class ServicioVehiculo implements IServicioVehiculo{

    private final List<Vehiculo> vehiculos = new ArrayList<>();

    @Autowired
    private ServicioUsuario servicioUsuario; // Inyección del servicio de usuario

    @Override
    public Vehiculo saveVehiculo(Vehiculo vehiculo, int usuarioId) {
        Usuario usuario = servicioUsuario.getUsuarioById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + usuarioId));

        if (!vehiculo.isSoat()) {
            throw new IllegalArgumentException("El vehículo debe tener SOAT válido para ser registrado.");
        }

        if (usuario.getVehiculos().stream().anyMatch(v -> v.getPlaca().equals(vehiculo.getPlaca()))) {
            throw new IllegalArgumentException("Un vehículo con la misma placa ya existe en este usuario");
        }

        // Añadir el vehículo a la lista del usuario
        if (usuario.getVehiculos() == null) {
            usuario.setVehiculos(new ArrayList<>());
        }
        usuario.getVehiculos().add(vehiculo);
        vehiculos.add(vehiculo);

        // Generar alerta según el resultado de la revisión técnica
        Alerta.Tipo tipoAlerta = vehiculo.isResultadoTecno() ? Alerta.Tipo.RevisionAprobada : Alerta.Tipo.RevisionReprobada;
        Alerta alerta = new Alerta();

        return vehiculo;
    }


    @Override
    public Optional<Vehiculo> getVehiculoByPlaca(String placa) {
        return vehiculos.stream()
                .filter(vehiculo -> vehiculo.getPlaca().equals(placa))
                .findFirst();
    }

    @Override
    public List<Vehiculo> getAllVehiculos() {
        return new ArrayList<>(vehiculos);
    }

    @Override
    public Vehiculo updateVehiculo(Vehiculo vehiculoNuevo, int usuarioId, String placa) {
        Usuario usuario = servicioUsuario.getUsuarioById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + usuarioId));

        Vehiculo vehiculoExistente = usuario.getVehiculos().stream()
                .filter(v -> v.getPlaca().equals(placa))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado con placa: " + placa));

        // Actualizar propiedades
        vehiculoExistente.setResultadoTecno(vehiculoNuevo.isResultadoTecno());
        vehiculoExistente.setSoat(vehiculoNuevo.isSoat());
        if (vehiculoNuevo instanceof Motocicleta && vehiculoExistente instanceof Motocicleta) {
            ((Motocicleta) vehiculoExistente).setCilindraje(((Motocicleta) vehiculoNuevo).getCilindraje());
        } else if (vehiculoNuevo instanceof Ligero && vehiculoExistente instanceof Ligero) {
            ((Ligero) vehiculoExistente).setNumAirbag(((Ligero) vehiculoNuevo).getNumAirbag());
            }

             // Generar alerta según el resultado de la revisión técnica
            Alerta.Tipo tipoAlerta = vehiculoExistente.isResultadoTecno() ? Alerta.Tipo.RevisionAprobada : Alerta.Tipo.RevisionReprobada;
             Alerta alerta = new Alerta();

             return vehiculoExistente;
    }

    public void deleteVehiculo(String placa, int usuarioId) {
        Usuario usuario = servicioUsuario.getUsuarioById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + usuarioId));

        boolean removed = usuario.getVehiculos().removeIf(v -> v.getPlaca().equals(placa));
        if (!removed) {
            throw new IllegalArgumentException("Vehículo no encontrado con placa: " + placa);
        }

        vehiculos.removeIf(v -> v.getPlaca().equals(placa));
    }

}
