package optimus_erp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ClienteRequestDTO {

    @NotBlank(message = "La razón social es obligatoria")
    private String razonSocial;

    @NotBlank(message = "El CIF/VIES es obligatorio")
    private String cifVies;

    private String pais = "España";

    @Email(message = "Formato de email inválido")
    private String emailFacturacion;

    public String getRazonSocial()                   { return razonSocial; }
    public void setRazonSocial(String r)             { this.razonSocial = r; }
    public String getCifVies()                        { return cifVies; }
    public void setCifVies(String c)                  { this.cifVies = c; }
    public String getPais()                           { return pais; }
    public void setPais(String p)                     { this.pais = p; }
    public String getEmailFacturacion()               { return emailFacturacion; }
    public void setEmailFacturacion(String e)         { this.emailFacturacion = e; }
}