package Servidor_CDA.Servidor_CDA.services;

import Servidor_CDA.Servidor_CDA.model.CertificadoTecnicoMecanica;
import Servidor_CDA.Servidor_CDA.repository.CertificadoTecnicoMecanicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificadoTecnicoMecanicaService {

    @Autowired
    private CertificadoTecnicoMecanicaRepository certificadoRepository;

    public List<CertificadoTecnicoMecanica> getAllCertificados() {
        return certificadoRepository.findAll();
    }

    public CertificadoTecnicoMecanica getCertificadoById(Long id) {
        return certificadoRepository.findById(id).orElse(null);
    }

    public CertificadoTecnicoMecanica createCertificado(CertificadoTecnicoMecanica certificado) {
        return certificadoRepository.save(certificado);
    }

    public void deleteCertificado(Long id) {
        certificadoRepository.deleteById(id);
    }
}
