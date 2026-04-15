package optimus_erp.model;

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
    private String numeroSerie;

    @Enumerated(EnumType.STRING)
    private TipoActivo tipo;

    private Double valor;

    @Column(name = "empleado_id")
    private Integer empleadoId;
}

enum TipoActivo {
    LAPTOP, PERIFERICO, LICENCIA, SERVIDOR
}
