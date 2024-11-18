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

    // Crear una nueva revisión y generar el certificado automáticamente
    @PostMapping("/{vehiculoPlaca}")
    public ResponseEntity<?> createRevision(
            @PathVariable String vehiculoPlaca,
            @RequestBody Revision revision,
            @RequestParam String username) {
        try {
            // Crear o actualizar la revisión usando el username
            Revision createdRevision = revisionService.createOrUpdateRevision(vehiculoPlaca, revision, username);
            return ResponseEntity.ok(createdRevision);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (MessagingException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al enviar el correo con el certificado.");
        } catch (DocumentException e) {
            return ResponseEntity.status(500).body("Error al generar el documento PDF del certificado.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error al procesar el archivo del certificado.");
        }
    }


    // Actualizar una revisión existente y generar el certificado automáticamente
    @PutMapping("/{vehiculoPlaca}")
    public ResponseEntity<?> updateRevision(@PathVariable String vehiculoPlaca, @RequestBody Revision revision, @RequestParam String username) {
        try {
            Revision updatedRevision = revisionService.createOrUpdateRevision(vehiculoPlaca, revision, username);
            return ResponseEntity.ok(updatedRevision);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (MessagingException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al enviar el correo con el certificado.");
        } catch (DocumentException e) {
            return ResponseEntity.status(500).body("Error al generar el documento PDF del certificado.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error al procesar el archivo del certificado.");
        }
    }
}
