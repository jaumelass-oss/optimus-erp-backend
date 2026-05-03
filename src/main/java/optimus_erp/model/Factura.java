package optimus_erp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "facturas")
@Data
@NoArgsConstructor
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "numero_factura", unique = true, length = 50)
    private String numeroFactura;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Column(name = "fecha_emision")
    private LocalDate fechaEmision;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoFactura estado = EstadoFactura.BORRADOR;

    @Column(name = "base_imponible", precision = 12, scale = 2)
    private BigDecimal baseImponible = BigDecimal.ZERO;

    @Column(name = "iva_porcentaje", precision = 4, scale = 2)
    private BigDecimal ivaPorcentaje = new BigDecimal("21.00");

    @Column(name = "total_eur", precision = 12, scale = 2)
    private BigDecimal totalEur = BigDecimal.ZERO;

    @Column(name = "xml_generado_path")
    private String xmlGeneradoPath;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LineaFactura> lineas = new ArrayList<>();

    public void recalcularTotales() {
        this.baseImponible = lineas.stream()
                .map(LineaFactura::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal iva = this.baseImponible
                .multiply(this.ivaPorcentaje)
                .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);

        this.totalEur = this.baseImponible.add(iva);
    }

    public void agregarLinea(LineaFactura linea) {
        linea.setFactura(this);
        this.lineas.add(linea);
        recalcularTotales();
    }
}