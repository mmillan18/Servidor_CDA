package Servidor_CDA.Servidor_CDA.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "revision")
public class Revision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date fechaRevision;

    private boolean resultadoRevision;

    // Relación con Vehículo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehiculo_id", referencedColumnName = "placa", nullable = false)
    private Vehiculo vehiculo;

    // Relación con Empleado (usuario que realiza la revisión)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empleado_encargado_id", nullable = false)
    private Empleado empleadoEncargado;
}
