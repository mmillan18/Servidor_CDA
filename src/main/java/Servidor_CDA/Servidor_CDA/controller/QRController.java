package Servidor_CDA.Servidor_CDA.controller;

import Servidor_CDA.Servidor_CDA.QRReportPDFGenerator;
import Servidor_CDA.Servidor_CDA.model.QR;
import Servidor_CDA.Servidor_CDA.services.ServicioQR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/CDA/qr")
public class QRController {

    @Autowired
    private ServicioQR servicioQR;

    @PostMapping
    public ResponseEntity<QR> agregarReporte(@RequestBody QR qrRequest) {
        try {
            if (qrRequest.getFechaCreacion() == null) {
                return ResponseEntity.badRequest().body(null);
            }

            QR nuevoReporte = servicioQR.agregarReporte(
                    qrRequest.getQuejasRecomendaciones(),
                    qrRequest.getFechaCreacion()
            );

            return ResponseEntity.status(201).body(nuevoReporte);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<QR>> obtenerTodosReportes() {
        List<QR> reportes = servicioQR.obtenerTodosReportes();
        return ResponseEntity.ok(reportes);
    }

    @GetMapping("/reporte/ano/{ano}")
    public ResponseEntity<ByteArrayResource> generarReportePorAno(@PathVariable int ano) {
        List<QR> reportes = servicioQR.obtenerReportesPorAno(ano);
        byte[] pdf = QRReportPDFGenerator.generateReport(reportes, "Reporte de Quejas y Recomendaciones - Año " + ano);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_qr_ano_" + ano + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(new ByteArrayResource(pdf));
    }

    @GetMapping("/reporte/mes/{ano}/{mes}")
    public ResponseEntity<ByteArrayResource> generarReportePorMes(@PathVariable int ano, @PathVariable int mes) {
        List<QR> reportes = servicioQR.obtenerReportesPorMes(ano, mes);
        byte[] pdf = QRReportPDFGenerator.generateReport(reportes, "Reporte de Quejas y Recomendaciones - Mes " + mes + " de " + ano);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_qr_" + ano + "_" + mes + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(new ByteArrayResource(pdf));
    }
}
