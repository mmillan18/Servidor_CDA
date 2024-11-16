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
    @PostMapping("/{vehiculoId}")
    public ResponseEntity<Revision> createRevision(@PathVariable String vehiculoId, @RequestBody Revision revision) {
        try {
            Revision createdRevision = revisionService.createOrUpdateRevision(vehiculoId, revision);
            return ResponseEntity.ok(createdRevision);
        } catch (MessagingException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        } catch (RuntimeException | DocumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Actualizar una revisión existente y generar el certificado automáticamente
    @PutMapping("/{vehiculoId}")
    public ResponseEntity<Revision> updateRevision(@PathVariable String vehiculoId, @RequestBody Revision revision) {
        try {
            Revision updatedRevision = revisionService.createOrUpdateRevision(vehiculoId, revision);
            return ResponseEntity.ok(updatedRevision);
        } catch (MessagingException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
