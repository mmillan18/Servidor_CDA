package Servidor_CDA.Servidor_CDA.services;

import Servidor_CDA.Servidor_CDA.model.QR;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioQR implements IServicioQR{
    private final List<QR> reportes = new ArrayList<>();

    @Override
    public QR agregarReporte(String contenido) {
        QR nuevoReporte = new QR(contenido);
        reportes.add(nuevoReporte);
        return nuevoReporte;
    }

    @Override
    public List<QR> obtenerTodosReportes() {
        return reportes;
    }
}
