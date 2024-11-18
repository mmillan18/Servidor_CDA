package Servidor_CDA.Servidor_CDA.controller;

import Servidor_CDA.Servidor_CDA.model.Revision;
import Servidor_CDA.Servidor_CDA.services.RevisionService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/CDA/revisiones")
public class RevisionController {
    @Autowired
    private RevisionService revisionService;

    // Obtener todas las revisiones
    @GetMapping
    public List<Revision> getAllRevisions() {
        return revisionService.getAllRevisions();
    }

    // Obtener una revisión por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Revision> getRevisionById(@PathVariable Long id) {
        Revision revision = revisionService.getRevisionById(id);
        return revision != null ? ResponseEntity.ok(revision) : ResponseEntity.notFound().build();
    }

    // Crear una nueva revisión
    @PostMapping("/{vehiculoPlaca}")
    public ResponseEntity<?> createRevision(
            @PathVariable String vehiculoPlaca,
            @RequestBody Revision revision,
            @RequestParam String username) {
        try {
            // Crear la revisión
            Revision createdRevision = revisionService.createOrUpdateRevision(vehiculoPlaca, revision, username);
            return ResponseEntity.ok(createdRevision);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al procesar la solicitud: " + e.getMessage());
        }
    }

    // Actualizar una revisión existente
    @PutMapping("/{vehiculoPlaca}")
    public ResponseEntity<?> updateRevision(
            @PathVariable String vehiculoPlaca,
            @RequestBody Revision revision,
            @RequestParam String username) {
        try {
            // Actualizar la revisión
            Revision updatedRevision = revisionService.createOrUpdateRevision(vehiculoPlaca, revision, username);
            return ResponseEntity.ok(updatedRevision);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al procesar la solicitud: " + e.getMessage());
        }
    }
}