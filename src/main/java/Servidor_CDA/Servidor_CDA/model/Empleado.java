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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cargo;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_ingreso", nullable = false)
    private Date fechaIngreso;

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
