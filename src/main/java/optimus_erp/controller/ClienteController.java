package optimus_erp.controller;

import jakarta.validation.Valid;
import optimus_erp.dto.ClienteRequestDTO;
import optimus_erp.dto.ClienteResponseDTO;
import optimus_erp.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    /** GET /api/clientes                     → todos */
    /** GET /api/clientes?nombre=Acme         → filtro por razón social */
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listar(
            @RequestParam(required = false) String nombre) {

        List<ClienteResponseDTO> resultado = (nombre != null && !nombre.isBlank())
                ? clienteService.buscarPorNombre(nombre)
                : clienteService.listarTodos();

        return ResponseEntity.ok(resultado);
    }

    /** GET /api/clientes/{id} */
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(clienteService.buscarPorId(id));
    }

    /** POST /api/clientes */
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> crear(@Valid @RequestBody ClienteRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.crear(dto));
    }

    /** PUT /api/clientes/{id} */
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody ClienteRequestDTO dto) {
        return ResponseEntity.ok(clienteService.actualizar(id, dto));
    }

    /** DELETE /api/clientes/{id} */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}