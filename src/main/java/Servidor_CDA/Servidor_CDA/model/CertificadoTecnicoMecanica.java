package Servidor_CDA.Servidor_CDA.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class CertificadoTecnicoMecanica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date fechaEmision;

    private Date fechaVencimiento;

    // Relación con la revisión
    @OneToOne
    @JoinColumn(name = "revision_id")
    private Revision revision;

}
