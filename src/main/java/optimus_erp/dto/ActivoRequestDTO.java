package optimus_erp.dto;

import optimus_erp.model.TipoActivo;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public class ActivoRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private String numeroSerie;
    private TipoActivo tipo;
    private Double valor;
    private int stock;
    private Integer empleadoId;

    // Historial
    private LocalDate fechaEntrada;
    private Integer clienteVendidoId;
    private LocalDate fechaVenta;
    private String notas;

    // Getters y Setters
    public String getNombre()                      { return nombre; }
    public void setNombre(String n)                { this.nombre = n; }
    public String getNumeroSerie()                 { return numeroSerie; }
    public void setNumeroSerie(String s)           { this.numeroSerie = s; }
    public TipoActivo getTipo()                    { return tipo; }
    public void setTipo(TipoActivo t)              { this.tipo = t; }
    public Double getValor()                       { return valor; }
    public void setValor(Double v)                 { this.valor = v; }
    public int getStock()                          { return stock; }
    public void setStock(int s)                    { this.stock = s; }
    public Integer getEmpleadoId()                 { return empleadoId; }
    public void setEmpleadoId(Integer e)           { this.empleadoId = e; }
    public LocalDate getFechaEntrada()             { return fechaEntrada; }
    public void setFechaEntrada(LocalDate f)       { this.fechaEntrada = f; }
    public Integer getClienteVendidoId()           { return clienteVendidoId; }
    public void setClienteVendidoId(Integer c)     { this.clienteVendidoId = c; }
    public LocalDate getFechaVenta()               { return fechaVenta; }
    public void setFechaVenta(LocalDate f)         { this.fechaVenta = f; }
    public String getNotas()                       { return notas; }
    public void setNotas(String n)                 { this.notas = n; }
}