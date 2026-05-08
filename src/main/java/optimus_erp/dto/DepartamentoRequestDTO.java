package optimus_erp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import optimus_erp.model.Sede;

public class DepartamentoRequestDTO {

    @NotBlank(message = "El nombre del departamento es obligatorio")
    private String nombre;

    @NotNull(message = "La sede es obligatoria")
    private Sede sede;

    public String getNombre()        { return nombre; }
    public void setNombre(String n)  { this.nombre = n; }
    public Sede getSede()            { return sede; }
    public void setSede(Sede s)      { this.sede = s; }
}