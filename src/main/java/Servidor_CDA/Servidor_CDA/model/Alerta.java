package Servidor_CDA.Servidor_CDA.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder

public class Alerta {

    public enum Tipo {
        RevisionReprobada, RevisionAprobada
    }

    private Tipo tipo;

}
