package optimus_erp.controller;

import jakarta.validation.Valid;
import optimus_erp.dto.FacturaRequestDTO;
import optimus_erp.dto.FacturaResponseDTO;
import optimus_erp.model.EstadoFactura;
import optimus_erp.service.FacturaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
@CrossOrigin(origins = "*")
public class FacturaController {

    private final FacturaService facturaService;

    public FacturaController(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    /** GET /api/facturas                        → todas */
    /** GET /api/facturas?clienteId=3            → por cliente */
    /** GET /api/facturas?estado=EMITIDA         → por estado  */
    @GetMapping
    public ResponseEntity<List<FacturaResponseDTO>> listar(
            @RequestParam(required = false) Integer clienteId,
            @RequestParam(required = false) EstadoFactura estado) {

        List<FacturaResponseDTO> resultado;
        if (clienteId != null)    resultado = facturaService.listarPorCliente(clienteId);
        else if (estado != null)  resultado = facturaService.listarPorEstado(estado);
        else                      resultado = facturaService.listarTodas();

        return ResponseEntity.ok(resultado);
    }

    /** GET /api/facturas/{id} */
    @GetMapping("/{id}")
    public ResponseEntity<FacturaResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(facturaService.buscarPorId(id));
    }

    /** POST /api/facturas */
    @PostMapping
    public ResponseEntity<FacturaResponseDTO> crear(@Valid @RequestBody FacturaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facturaService.crear(dto));
    }

    /** PUT /api/facturas/{id}  (solo en BORRADOR) */
    @PutMapping("/{id}")
    public ResponseEntity<FacturaResponseDTO> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody FacturaRequestDTO dto) {
        return ResponseEntity.ok(facturaService.actualizar(id, dto));
    }

    /**
     * PATCH /api/facturas/{id}/estado
     * Body JSON: "EMITIDA" | "PAGADA" | "CANCELADA"
     */
    @PatchMapping("/{id}/estado")
    public ResponseEntity<FacturaResponseDTO> cambiarEstado(
            @PathVariable Integer id,
            @RequestBody EstadoFactura nuevoEstado) {
        return ResponseEntity.ok(facturaService.cambiarEstado(id, nuevoEstado));
    }

    /** DELETE /api/facturas/{id}  (solo en BORRADOR) */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        facturaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}