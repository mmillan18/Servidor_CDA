package Servidor_CDA.Servidor_CDA.services;

import Servidor_CDA.Servidor_CDA.model.QR;
import Servidor_CDA.Servidor_CDA.repository.QRRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ServicioQR implements IServicioQR {

    @Autowired
    private QRRepository qrRepository;

    @Override
    public QR agregarReporte(String contenido) {
        QR nuevoReporte = QR.builder()
                .quejasRecomendaciones(contenido)
                .build();
        return qrRepository.save(nuevoReporte);
    }

    @Override
    public List<QR> obtenerTodosReportes() {
        return qrRepository.findAll();
    }

    public List<QR> obtenerReportesPorAno(int ano) {
        Calendar inicio = Calendar.getInstance();
        inicio.set(ano, Calendar.JANUARY, 1, 0, 0, 0);
        Date fechaInicio = inicio.getTime();

        Calendar fin = Calendar.getInstance();
        fin.set(ano, Calendar.DECEMBER, 31, 23, 59, 59);
        Date fechaFin = fin.getTime();

        return qrRepository.findByFechaCreacionBetween(fechaInicio, fechaFin);
    }

    public List<QR> obtenerReportesPorMes(int ano, int mes) {
        Calendar inicio = Calendar.getInstance();
        inicio.set(ano, mes - 1, 1, 0, 0, 0);
        Date fechaInicio = inicio.getTime();

        Calendar fin = Calendar.getInstance();
        fin.set(ano, mes - 1, inicio.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
        Date fechaFin = fin.getTime();

        return qrRepository.findByFechaCreacionBetween(fechaInicio, fechaFin);
    }
}
