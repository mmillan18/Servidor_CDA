package Servidor_CDA.Servidor_CDA.controller;

import Servidor_CDA.Servidor_CDA.model.QR;
import Servidor_CDA.Servidor_CDA.services.ServicioQR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/CDA/qr")

public class QRController {

    @Autowired
    private ServicioQR servicioQR;

    @PostMapping
    public ResponseEntity<QR> agregarReporte(@RequestBody String contenido) {
        QR nuevoReporte = servicioQR.agregarReporte(contenido);
        return new ResponseEntity<>(nuevoReporte, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<QR>> obtenerTodosReportes() {
        List<QR> reportes = servicioQR.obtenerTodosReportes();
        return ResponseEntity.ok(reportes);
    }
}
