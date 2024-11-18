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

    /**
     * Agregar un nuevo reporte de QR.
     *
     * @param contenido Contenido del reporte.
     * @return QR guardado.
     */
    @Override
    public QR agregarReporte(String contenido, Date fechaCreacion) {
        if (fechaCreacion == null) {
            throw new IllegalArgumentException("La fecha de creación no puede ser nula.");
        }

        QR nuevoReporte = QR.builder()
                .quejasRecomendaciones(contenido)
                .fechaCreacion(fechaCreacion)
                .build();

        return qrRepository.save(nuevoReporte);
    }


    /**
     * Obtener todos los reportes de QR.
     *
     * @return Lista de todos los QR.
     */
    @Override
    public List<QR> obtenerTodosReportes() {
        return qrRepository.findAll();
    }

    /**
     * Obtener reportes de QR por año.
     *
     * @param ano Año para filtrar.
     * @return Lista de reportes en el año dado.
     */
    public List<QR> obtenerReportesPorAno(int ano) {
        Calendar inicio = Calendar.getInstance();
        inicio.set(ano, Calendar.JANUARY, 1, 0, 0, 0);
        inicio.set(Calendar.MILLISECOND, 0); // Asegurar precisión
        Date fechaInicio = inicio.getTime();

        Calendar fin = Calendar.getInstance();
        fin.set(ano, Calendar.DECEMBER, 31, 23, 59, 59);
        fin.set(Calendar.MILLISECOND, 999); // Asegurar precisión
        Date fechaFin = fin.getTime();

        return qrRepository.findByFechaCreacionBetween(fechaInicio, fechaFin);
    }

    /**
     * Obtener reportes de QR por año y mes.
     *
     * @param ano Año para filtrar.
     * @param mes Mes para filtrar (1 = enero, 12 = diciembre).
     * @return Lista de reportes en el mes y año dados.
     */
    public List<QR> obtenerReportesPorMes(int ano, int mes) {
        Calendar inicio = Calendar.getInstance();
        inicio.set(ano, mes - 1, 1, 0, 0, 0);
        inicio.set(Calendar.MILLISECOND, 0); // Asegurar precisión
        Date fechaInicio = inicio.getTime();

        Calendar fin = Calendar.getInstance();
        fin.set(ano, mes - 1, inicio.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
        fin.set(Calendar.MILLISECOND, 999); // Asegurar precisión
        Date fechaFin = fin.getTime();

        return qrRepository.findByFechaCreacionBetween(fechaInicio, fechaFin);
    }
}
