package optimus_erp.dto;

import optimus_erp.model.Cliente;

public class ClienteResponseDTO {

    private Integer id;
    private String razonSocial;
    private String cifVies;
    private String pais;
    private String emailFacturacion;

    public static ClienteResponseDTO desde(Cliente c) {
        ClienteResponseDTO dto = new ClienteResponseDTO();
        dto.id               = c.getId();
        dto.razonSocial      = c.getRazonSocial();
        dto.cifVies          = c.getCifVies();
        dto.pais             = c.getPais();
        dto.emailFacturacion = c.getEmailFacturacion();
        return dto;
    }

    public Integer getId()              { return id; }
    public String getRazonSocial()      { return razonSocial; }
    public String getCifVies()          { return cifVies; }
    public String getPais()             { return pais; }
    public String getEmailFacturacion() { return emailFacturacion; }
}