package optimus_erp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "empleados")
@Data
@NoArgsConstructor
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellidos;

    @Column(name = "email_corporativo", unique = true, length = 100)
    private String emailCorporativo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departamento_id")
    private Departamento departamento;

    @Column(name = "usuario_id")
    private Integer usuarioId;

    @Column(name = "tarifa_hora", precision = 10, scale = 2)
    private BigDecimal tarifaHora = BigDecimal.ZERO;
}