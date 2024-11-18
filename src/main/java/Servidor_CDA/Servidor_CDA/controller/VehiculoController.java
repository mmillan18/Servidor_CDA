package Servidor_CDA.Servidor_CDA.controller;

import Servidor_CDA.Servidor_CDA.model.Usuario;
import Servidor_CDA.Servidor_CDA.model.Vehiculo;
import Servidor_CDA.Servidor_CDA.services.ServicioVehiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/CDA/vehiculos")
public class VehiculoController {

    @Autowired
    private ServicioVehiculo servicioVehiculo;

    @PostMapping(value = "/{usuarioId}", consumes = {"multipart/form-data"})
    public ResponseEntity<?> createVehiculo(
            @PathVariable int usuarioId,
            @RequestPart("vehiculo") Vehiculo vehiculo,
            @RequestPart("imagen") MultipartFile imagen) {
        try {
            // Validar que la imagen no esté vacía
            if (imagen.isEmpty()) {
                return ResponseEntity.badRequest().body("La imagen no puede estar vacía.");
            }

            // Guardar el vehículo con la imagen
            Vehiculo newVehiculo = servicioVehiculo.saveVehiculoWithImage(vehiculo, usuarioId, imagen);
            return new ResponseEntity<>(newVehiculo, HttpStatus.CREATED);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error procesando la imagen.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Vehiculo>> getVehiculosByUsuarioId(@PathVariable int usuarioId) {
        try {
            List<Vehiculo> vehiculos = servicioVehiculo.getVehiculosByUsuario(usuarioId);
            if (vehiculos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(vehiculos);
            }
            return ResponseEntity.ok(vehiculos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{placa}")
    public ResponseEntity<Vehiculo> getVehiculoByPlaca(@PathVariable String placa) {
        Optional<Vehiculo> vehiculo = servicioVehiculo.getVehiculoByPlaca(placa);
        return vehiculo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Vehiculo>> getAllVehiculos() {
        List<Vehiculo> vehiculos = servicioVehiculo.getAllVehiculos();
        return ResponseEntity.ok(vehiculos);
    }

    @PutMapping("/{usuarioId}/{placa}")
    public ResponseEntity<?> updateVehiculo(@PathVariable int usuarioId, @PathVariable String placa, @RequestBody Vehiculo vehiculo) {
        try {
            Vehiculo updatedVehiculo = servicioVehiculo.updateVehiculo(vehiculo, usuarioId, placa);
            return ResponseEntity.ok(updatedVehiculo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{usuarioId}/{placa}")
    public ResponseEntity<Void> deleteVehiculo(@PathVariable int usuarioId, @PathVariable String placa) {
        try {
            servicioVehiculo.deleteVehiculo(placa, usuarioId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
