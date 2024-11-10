package Servidor_CDA.Servidor_CDA.controller;
import Servidor_CDA.Servidor_CDA.model.Revision;
import Servidor_CDA.Servidor_CDA.services.RevisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/revisiones")
public class RevisionController {

    @Autowired
    private RevisionService revisionService;

    @GetMapping
    public List<Revision> getAllRevisions() {
        return revisionService.getAllRevisions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Revision> getRevisionById(@PathVariable Long id) {
        Revision revision = revisionService.getRevisionById(id);
        return revision != null ? ResponseEntity.ok(revision) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Revision createRevision(@RequestBody Revision revision) {
        return revisionService.createRevision(revision);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRevision(@PathVariable Long id) {
        revisionService.deleteRevision(id);
        return ResponseEntity.noContent().build();
    }
}
