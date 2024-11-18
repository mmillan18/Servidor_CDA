package Servidor_CDA.Servidor_CDA.model;

import Servidor_CDA.Servidor_CDA.model.Ligero;
import Servidor_CDA.Servidor_CDA.model.Motocicleta;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "vehiculo")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_vehiculo", discriminatorType = DiscriminatorType.STRING)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "tipoVehiculo"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Motocicleta.class, name = "motocicleta"),
        @JsonSubTypes.Type(value = Ligero.class, name = "ligero")
})
public class Vehiculo {

    @Id
    @Column(name = "placa", nullable = false)
    private String placa;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "soat")
    private boolean soat;

   @Column(name = "img_url")
    private String img_url;

    // Clase Vehiculo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_cedula", nullable = false)
    @JsonIgnoreProperties("vehiculos") // Ignora la lista de vehículos al serializar
    private Usuario usuario;

}
