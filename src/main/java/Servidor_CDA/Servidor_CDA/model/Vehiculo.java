package Servidor_CDA.Servidor_CDA.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder

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

    private String placa;
    private LocalDate fecha;
    private boolean resultadoTecno;
    private boolean soat;
}
