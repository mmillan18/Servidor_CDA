package Servidor_CDA.Servidor_CDA.controller;
import Servidor_CDA.Servidor_CDA.model.CertificadoTecnicoMecanica;
import Servidor_CDA.Servidor_CDA.services.CertificadoTecnicoMecanicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certificados")
public class CertificadoTecnicoMecanicaController {

    @Autowired
    private CertificadoTecnicoMecanicaService certificadoService;

    @GetMapping
    public List<CertificadoTecnicoMecanica> getAllCertificados() {
        return certificadoService.getAllCertificados();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificadoTecnicoMecanica> getCertificadoById(@PathVariable Long id) {
        CertificadoTecnicoMecanica certificado = certificadoService.getCertificadoById(id);
        return certificado != null ? ResponseEntity.ok(certificado) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public CertificadoTecnicoMecanica createCertificado(@RequestBody CertificadoTecnicoMecanica certificado) {
        return certificadoService.createCertificado(certificado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCertificado(@PathVariable Long id) {
        certificadoService.deleteCertificado(id);
        return ResponseEntity.noContent().build();
    }
}
