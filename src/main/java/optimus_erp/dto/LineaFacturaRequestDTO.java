package optimus_erp.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class LineaFacturaRequestDTO {

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "La cantidad debe ser mayor que 0")
    private Integer cantidad;

    @NotNull(message = "El precio unitario es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor que 0")
    private BigDecimal precioUnitario;

    private Long activoId;

    public String getDescripcion()              { return descripcion; }
    public void setDescripcion(String d)        { this.descripcion = d; }
    public Integer getCantidad()                { return cantidad; }
    public void setCantidad(Integer c)          { this.cantidad = c; }
    public BigDecimal getPrecioUnitario()       { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal p) { this.precioUnitario = p; }
    public Long getActivoId()                   { return activoId; }
    public void setActivoId(Long id)            { this.activoId = id; }
}
