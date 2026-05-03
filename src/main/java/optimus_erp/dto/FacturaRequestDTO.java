package optimus_erp.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class FacturaRequestDTO {

    @NotNull(message = "El cliente es obligatorio")
    private Integer clienteId;

    @NotNull(message = "La fecha de emisión es obligatoria")
    private LocalDate fechaEmision;

    /** Por defecto 21 %. Ajustar para exentos (0) u otros tipos */
    private BigDecimal ivaPorcentaje = new BigDecimal("21.00");

    @NotEmpty(message = "La factura debe tener al menos una línea")
    @Valid
    private List<LineaFacturaRequestDTO> lineas;

    public Integer getClienteId()                          { return clienteId; }
    public void setClienteId(Integer id)                   { this.clienteId = id; }
    public LocalDate getFechaEmision()                     { return fechaEmision; }
    public void setFechaEmision(LocalDate f)               { this.fechaEmision = f; }
    public BigDecimal getIvaPorcentaje()                   { return ivaPorcentaje; }
    public void setIvaPorcentaje(BigDecimal p)             { this.ivaPorcentaje = p; }
    public List<LineaFacturaRequestDTO> getLineas()        { return lineas; }
    public void setLineas(List<LineaFacturaRequestDTO> l)  { this.lineas = l; }
}