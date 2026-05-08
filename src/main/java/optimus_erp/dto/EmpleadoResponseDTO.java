package optimus_erp.dto;

import optimus_erp.model.Empleado;
import java.math.BigDecimal;

public class EmpleadoResponseDTO {

    private Integer id;
    private String nombre;
    private String apellidos;
    private String nombreCompleto;
    private String emailCorporativo;
    private Integer departamentoId;
    private String departamentoNombre;
    private BigDecimal tarifaHora;

    public static EmpleadoResponseDTO desde(Empleado e) {
        EmpleadoResponseDTO dto = new EmpleadoResponseDTO();
        dto.id                  = e.getId();
        dto.nombre              = e.getNombre();
        dto.apellidos           = e.getApellidos();
        dto.nombreCompleto      = e.getNombre() + " " + e.getApellidos();
        dto.emailCorporativo    = e.getEmailCorporativo();
        dto.tarifaHora          = e.getTarifaHora();
        if (e.getDepartamento() != null) {
            dto.departamentoId     = e.getDepartamento().getId();
            dto.departamentoNombre = e.getDepartamento().getNombre();
        }
        return dto;
    }

    public Integer getId()                { return id; }
    public String getNombre()             { return nombre; }
    public String getApellidos()          { return apellidos; }
    public String getNombreCompleto()     { return nombreCompleto; }
    public String getEmailCorporativo()   { return emailCorporativo; }
    public Integer getDepartamentoId()    { return departamentoId; }
    public String getDepartamentoNombre() { return departamentoNombre; }
    public BigDecimal getTarifaHora()     { return tarifaHora; }
}