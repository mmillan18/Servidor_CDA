package Servidor_CDA.Servidor_CDA.model;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Revision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date fechaRevision;

    private boolean resultadoRevision;

    // Relación con vehículo
    @ManyToOne
    @JoinColumn(name = "vehiculo_id")
    private Vehiculo vehiculo;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaRevision() {
        return fechaRevision;
    }

    public void setFechaRevision(Date fechaRevision) {
        this.fechaRevision = fechaRevision;
    }

    public boolean isResultadoRevision() {
        return resultadoRevision;
    }

    public void setResultadoRevision(boolean resultadoRevision) {
        this.resultadoRevision = resultadoRevision;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }
}
