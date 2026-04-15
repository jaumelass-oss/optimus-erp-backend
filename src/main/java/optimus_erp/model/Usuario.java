package optimus_erp.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    private Boolean activo;
}

enum Rol {
    ADMIN, RRHH, DEV, COMERCIAL, FINANCE
}
