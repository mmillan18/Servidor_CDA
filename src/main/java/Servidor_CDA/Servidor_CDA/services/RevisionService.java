package Servidor_CDA.Servidor_CDA.services;

import Servidor_CDA.Servidor_CDA.model.Revision;
import Servidor_CDA.Servidor_CDA.repository.RevisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RevisionService {

    @Autowired
    private RevisionRepository revisionRepository;

    public List<Revision> getAllRevisions() {
        return revisionRepository.findAll();
    }

    public Revision getRevisionById(Long id) {
        return revisionRepository.findById(id).orElse(null);
    }

    public Revision createRevision(Revision revision) {
        return revisionRepository.save(revision);
    }

    public void deleteRevision(Long id) {
        revisionRepository.deleteById(id);
    }
}
