package optimus_erp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table (name = "activos")
@Data
public class Activo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(name = "numero_serie")
    @JsonProperty("numero_serie")
    private String numeroSerie;

    @Enumerated(EnumType.STRING)
    private TipoActivo tipo;

    private Double valor;

    private int stock;

    @Column(name = "empleado_id")
    private Integer empleadoId;
}

enum TipoActivo {
    LAPTOP, PERIFERICO, LICENCIA, SERVIDOR
}
