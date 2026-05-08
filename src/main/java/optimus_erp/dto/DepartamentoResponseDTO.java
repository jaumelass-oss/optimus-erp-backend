package optimus_erp.dto;

import optimus_erp.model.Departamento;
import optimus_erp.model.Sede;

public class DepartamentoResponseDTO {

    private Integer id;
    private String nombre;
    private Sede sede;
    private int totalEmpleados;

    public static DepartamentoResponseDTO desde(Departamento d) {
        DepartamentoResponseDTO dto = new DepartamentoResponseDTO();
        dto.id             = d.getId();
        dto.nombre         = d.getNombre();
        dto.sede           = d.getSede();
        dto.totalEmpleados = d.getEmpleados().size();
        return dto;
    }

    public Integer getId()          { return id; }
    public String getNombre()       { return nombre; }
    public Sede getSede()           { return sede; }
    public int getTotalEmpleados()  { return totalEmpleados; }
}