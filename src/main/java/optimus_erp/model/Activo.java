package optimus_erp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

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

    @Column(name = "fecha_entrada")
    private LocalDate fechaEntrada;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_vendido_id")
    private Cliente clienteVendido;

    @Column(name = "fecha_venta")
    private LocalDate fechaVenta;

    @Column(length = 500)
    private String notas;
}
