package optimus_erp.dto;

import optimus_erp.model.LineaFactura;
import java.math.BigDecimal;

public class LineaFacturaResponseDTO {

    private Integer id;
    private String descripcion;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
    private Long activoId;
    private String activoNombre;

    public static LineaFacturaResponseDTO desde(LineaFactura l) {
        LineaFacturaResponseDTO dto = new LineaFacturaResponseDTO();
        dto.id             = l.getId();
        dto.descripcion    = l.getDescripcion();
        dto.cantidad       = l.getCantidad();
        dto.precioUnitario = l.getPrecioUnitario();
        dto.subtotal       = l.getSubtotal();
        if (l.getActivo() != null) {
            dto.activoId     = l.getActivo().getId();
            dto.activoNombre = l.getActivo().getNombre();
        }
        return dto;
    }

    public Integer getId()                { return id; }
    public String getDescripcion()        { return descripcion; }
    public Integer getCantidad()          { return cantidad; }
    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public BigDecimal getSubtotal()       { return subtotal; }
    public Long getActivoId()             { return activoId; }
    public String getActivoNombre()       { return activoNombre; }
}