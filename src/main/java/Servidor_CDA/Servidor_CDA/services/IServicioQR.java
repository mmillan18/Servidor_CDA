package Servidor_CDA.Servidor_CDA.services;

import Servidor_CDA.Servidor_CDA.model.QR;

import java.util.List;

public interface IServicioQR {

    QR agregarReporte(String contenido);
    List<QR> obtenerTodosReportes();
}
