package Servidor_CDA.Servidor_CDA.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "empleado")
public class Empleado {

    @Id
    @Column(name = "cedula", nullable = false, unique = true)
    private int cedula;

    @Column(name = "cargo", nullable = false, length = 50)
    private String cargo;

    @Column(name = "fecha_ingreso", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaIngreso;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "rol", nullable = false, length = 20) // 'EMPLOYEE' o 'ADMIN'
    private String rol;

    // Relación con Revision
    @OneToMany(mappedBy = "empleadoEncargado", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Permite serializar las revisiones asociadas al empleado
    private List<Revision> revisiones;

    // Relación con QR
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "registro_qr_id", referencedColumnName = "id")
    private QR registroQR;

    // Constructor con valores por defecto
    @PrePersist
    protected void prePersist() {
        if (cargo == null) {
            cargo = "EMPLOYEE";
        }
        if (fechaIngreso == null) {
            fechaIngreso = new Date();
        }
    }
}
