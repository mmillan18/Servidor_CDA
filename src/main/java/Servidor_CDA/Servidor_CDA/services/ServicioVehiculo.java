package Servidor_CDA.Servidor_CDA.services;

import Servidor_CDA.Servidor_CDA.model.*;
import Servidor_CDA.Servidor_CDA.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioVehiculo implements IServicioVehiculo {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private ServicioUsuario servicioUsuario;

    @Override
    public Vehiculo saveVehiculo(Vehiculo vehiculo, int usuarioCedula) {
        Usuario usuario = servicioUsuario.getUsuarioById(usuarioCedula)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con cédula: " + usuarioCedula));

        if (!vehiculo.isSoat()) {
            throw new IllegalArgumentException("El vehículo debe tener SOAT válido para ser registrado.");
        }

        if (vehiculoRepository.existsByPlacaAndUsuarioCedula(vehiculo.getPlaca(), usuarioCedula)) {
            throw new IllegalArgumentException("Un vehículo con la misma placa ya existe para este usuario.");
        }

        vehiculo.setUsuario(usuario);
        return vehiculoRepository.save(vehiculo);
    }

    @Override
    public Optional<Vehiculo> getVehiculoByPlaca(String placa) {
        return vehiculoRepository.findById(placa);
    }

    @Override
    public List<Vehiculo> getVehiculosByUsuario(int usuarioId) {
        return vehiculoRepository.findByUsuarioCedula(usuarioId);
    }

    @Override
    public List<Vehiculo> getAllVehiculos() {
        return vehiculoRepository.findAll();
    }

    @Override
    public Vehiculo updateVehiculo(Vehiculo vehiculoNuevo, int usuarioCedula, String placa) {
        Usuario usuario = servicioUsuario.getUsuarioById(usuarioCedula)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con cédula: " + usuarioCedula));

        Vehiculo vehiculoExistente = vehiculoRepository.findByPlacaAndUsuarioCedula(placa, usuarioCedula)
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado con placa: " + placa));

        vehiculoExistente.setSoat(vehiculoNuevo.isSoat());

        if (vehiculoNuevo instanceof Motocicleta && vehiculoExistente instanceof Motocicleta) {
            ((Motocicleta) vehiculoExistente).setCilindraje(((Motocicleta) vehiculoNuevo).getCilindraje());
        } else if (vehiculoNuevo instanceof Ligero && vehiculoExistente instanceof Ligero) {
            ((Ligero) vehiculoExistente).setNumAirbag(((Ligero) vehiculoNuevo).getNumAirbag());
        }

        return vehiculoRepository.save(vehiculoExistente);
    }

    @Override
    public void deleteVehiculo(String placa, int usuarioCedula) {
        Usuario usuario = servicioUsuario.getUsuarioById(usuarioCedula)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con cédula: " + usuarioCedula));

        Optional<Vehiculo> vehiculo = vehiculoRepository.findByPlacaAndUsuarioCedula(placa, usuarioCedula);
        if (vehiculo.isPresent()) {
            vehiculoRepository.delete(vehiculo.get());
        } else {
            throw new IllegalArgumentException("Vehículo no encontrado con placa: " + placa);
        }
    }
}
