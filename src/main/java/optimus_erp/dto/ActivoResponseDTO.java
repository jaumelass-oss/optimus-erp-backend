package optimus_erp.dto;

import optimus_erp.model.Activo;
import optimus_erp.model.TipoActivo;
import java.time.LocalDate;

public class ActivoResponseDTO {

    private Long id;
    private String nombre;
    private String numeroSerie;
    private TipoActivo tipo;
    private Double valor;
    private int stock;
    private Integer empleadoId;

    // Historial
    private LocalDate fechaEntrada;
    private Integer clienteVendidoId;
    private String clienteVendidoNombre;
    private LocalDate fechaVenta;
    private String notas;

    public static ActivoResponseDTO desde(Activo a) {
        ActivoResponseDTO dto = new ActivoResponseDTO();
        dto.id           = a.getId();
        dto.nombre       = a.getNombre();
        dto.numeroSerie  = a.getNumeroSerie();
        dto.tipo         = a.getTipo();
        dto.valor        = a.getValor();
        dto.stock        = a.getStock();
        dto.empleadoId   = a.getEmpleadoId();
        dto.fechaEntrada = a.getFechaEntrada();
        dto.fechaVenta   = a.getFechaVenta();
        dto.notas        = a.getNotas();
        if (a.getClienteVendido() != null) {
            dto.clienteVendidoId     = a.getClienteVendido().getId();
            dto.clienteVendidoNombre = a.getClienteVendido().getRazonSocial();
        }
        return dto;
    }

    // Getters
    public Long getId()                    { return id; }
    public String getNombre()              { return nombre; }
    public String getNumeroSerie()         { return numeroSerie; }
    public TipoActivo getTipo()            { return tipo; }
    public Double getValor()               { return valor; }
    public int getStock()                  { return stock; }
    public Integer getEmpleadoId()         { return empleadoId; }
    public LocalDate getFechaEntrada()     { return fechaEntrada; }
    public Integer getClienteVendidoId()   { return clienteVendidoId; }
    public String getClienteVendidoNombre(){ return clienteVendidoNombre; }
    public LocalDate getFechaVenta()       { return fechaVenta; }
    public String getNotas()               { return notas; }
}
