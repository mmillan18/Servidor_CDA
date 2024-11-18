package Servidor_CDA.Servidor_CDA.services;

import Servidor_CDA.Servidor_CDA.model.*;
import Servidor_CDA.Servidor_CDA.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ServicioVehiculo implements IServicioVehiculo {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private ServicioUsuario servicioUsuario;

    private final String uploadDir = "uploads/vehiculos";

    @Override
    public Vehiculo saveVehiculoWithImage(Vehiculo vehiculo, int usuarioCedula, MultipartFile imageFile) throws IOException {
        // Buscar el usuario asociado
        Usuario usuario = servicioUsuario.getUsuarioById(usuarioCedula)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con cédula: " + usuarioCedula));

        // Validar el SOAT
        if (!vehiculo.isSoat()) {
            throw new IllegalArgumentException("El vehículo debe tener SOAT válido para ser registrado.");
        }

        // Validar si ya existe un vehículo con la misma placa
        if (vehiculoRepository.existsByPlacaAndUsuarioCedula(vehiculo.getPlaca(), usuarioCedula)) {
            throw new IllegalArgumentException("Un vehículo con la misma placa ya existe para este usuario.");
        }

        // Procesar la imagen y obtener la URL o ruta
        String imageUrl = saveImagen(imageFile);
        vehiculo.setImg_url(imageUrl);

        // Asociar el vehículo al usuario
        vehiculo.setUsuario(usuario);

        // Guardar el vehículo en la base de datos
        Vehiculo savedVehiculo = vehiculoRepository.save(vehiculo);

        // Añadir el vehículo a la lista del usuario si aún no está presente
        if (!usuario.getVehiculos().contains(savedVehiculo)) {
            usuario.getVehiculos().add(savedVehiculo);
        }

        return savedVehiculo;
    }


    // Método para guardar la imagen en el servidor
    private String saveImagen(MultipartFile imageFile) throws IOException {
        String folderPath = "uploads/"; // Carpeta donde se guardarán las imágenes
        String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();

        Path filePath = Paths.get(folderPath, fileName);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, imageFile.getBytes());

        return filePath.toString();
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
