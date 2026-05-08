package optimus_erp.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class EmpleadoRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "Los apellidos son obligatorios")
    private String apellidos;

    @Email(message = "Formato de email inválido")
    private String emailCorporativo;

    private Integer departamentoId;

    @DecimalMin(value = "0.0", message = "La tarifa no puede ser negativa")
    private BigDecimal tarifaHora = BigDecimal.ZERO;

    public String getNombre()                    { return nombre; }
    public void setNombre(String n)              { this.nombre = n; }
    public String getApellidos()                 { return apellidos; }
    public void setApellidos(String a)           { this.apellidos = a; }
    public String getEmailCorporativo()          { return emailCorporativo; }
    public void setEmailCorporativo(String e)    { this.emailCorporativo = e; }
    public Integer getDepartamentoId()           { return departamentoId; }
    public void setDepartamentoId(Integer id)    { this.departamentoId = id; }
    public BigDecimal getTarifaHora()            { return tarifaHora; }
    public void setTarifaHora(BigDecimal t)      { this.tarifaHora = t; }
}