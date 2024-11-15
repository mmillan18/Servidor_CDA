package Servidor_CDA.Servidor_CDA.model;

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
    @Column(name = "cedula")
    private int cedula;

    @Column(name = "cargo", nullable = false)
    private String cargo = "EMPLOYEE";

    @Column(name = "fecha_ingreso", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaIngreso = new Date();

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String rol; // 'EMPLOYEE' o 'ADMIN'

    // Relación con Revision
    @OneToMany(mappedBy = "empleadoEncargado", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Revision> revisiones;

    // Relación con RegistroQ&R
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "registro_qr_id", referencedColumnName = "id")
    private QR registroQR;
}
