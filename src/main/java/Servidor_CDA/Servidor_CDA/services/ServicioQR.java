package Servidor_CDA.Servidor_CDA.services;

import Servidor_CDA.Servidor_CDA.model.QR;
import Servidor_CDA.Servidor_CDA.repository.QRRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioQR implements IServicioQR {

    @Autowired
    private QRRepository qrRepository;

    @Override
    public QR agregarReporte(String contenido) {
        // Usa el patrón builder o el constructor completo para instanciar QR
        QR nuevoReporte = QR.builder()
                .quejasRecomendaciones(contenido)
                .build();
        return qrRepository.save(nuevoReporte);
    }

    @Override
    public List<QR> obtenerTodosReportes() {
        return qrRepository.findAll();
    }
}
