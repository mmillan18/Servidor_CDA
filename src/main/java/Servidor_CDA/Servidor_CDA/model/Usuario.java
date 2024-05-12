package Servidor_CDA.Servidor_CDA.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {


    private int cedula;
    private String nombre;
    private String correo;
    private List<Vehiculo> vehiculos;
}
