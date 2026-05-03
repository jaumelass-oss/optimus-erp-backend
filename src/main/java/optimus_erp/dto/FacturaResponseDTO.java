package optimus_erp.dto;

import optimus_erp.model.EstadoFactura;
import optimus_erp.model.Factura;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class FacturaResponseDTO {

    private Integer id;
    private String numeroFactura;
    private Integer clienteId;
    private String clienteRazonSocial;
    private LocalDate fechaEmision;
    private EstadoFactura estado;
    private BigDecimal baseImponible;
    private BigDecimal ivaPorcentaje;
    private BigDecimal totalEur;
    private List<LineaFacturaResponseDTO> lineas;

    public static FacturaResponseDTO desde(Factura f) {
        FacturaResponseDTO dto = new FacturaResponseDTO();
        dto.id                 = f.getId();
        dto.numeroFactura      = f.getNumeroFactura();
        dto.clienteId          = f.getCliente().getId();
        dto.clienteRazonSocial = f.getCliente().getRazonSocial();
        dto.fechaEmision       = f.getFechaEmision();
        dto.estado             = f.getEstado();
        dto.baseImponible      = f.getBaseImponible();
        dto.ivaPorcentaje      = f.getIvaPorcentaje();
        dto.totalEur           = f.getTotalEur();
        dto.lineas             = f.getLineas().stream()
                .map(LineaFacturaResponseDTO::desde)
                .collect(Collectors.toList());
        return dto;
    }

    public Integer getId()                           { return id; }
    public String getNumeroFactura()                 { return numeroFactura; }
    public Integer getClienteId()                    { return clienteId; }
    public String getClienteRazonSocial()            { return clienteRazonSocial; }
    public LocalDate getFechaEmision()               { return fechaEmision; }
    public EstadoFactura getEstado()                 { return estado; }
    public BigDecimal getBaseImponible()             { return baseImponible; }
    public BigDecimal getIvaPorcentaje()             { return ivaPorcentaje; }
    public BigDecimal getTotalEur()                  { return totalEur; }
    public List<LineaFacturaResponseDTO> getLineas() { return lineas; }
}