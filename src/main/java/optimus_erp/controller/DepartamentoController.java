package optimus_erp.controller;

import jakarta.validation.Valid;
import optimus_erp.dto.DepartamentoRequestDTO;
import optimus_erp.dto.DepartamentoResponseDTO;
import optimus_erp.service.DepartamentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departamentos")
@CrossOrigin(origins = "*")
public class DepartamentoController {

    private final DepartamentoService departamentoService;

    public DepartamentoController(DepartamentoService departamentoService) {
        this.departamentoService = departamentoService;
    }

    /** GET /api/departamentos */
    @GetMapping
    public ResponseEntity<List<DepartamentoResponseDTO>> listar() {
        return ResponseEntity.ok(departamentoService.listarTodos());
    }

    /** GET /api/departamentos/{id} */
    @GetMapping("/{id}")
    public ResponseEntity<DepartamentoResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(departamentoService.buscarPorId(id));
    }

    /** POST /api/departamentos */
    @PostMapping
    public ResponseEntity<DepartamentoResponseDTO> crear(
            @Valid @RequestBody DepartamentoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(departamentoService.crear(dto));
    }

    /** PUT /api/departamentos/{id} */
    @PutMapping("/{id}")
    public ResponseEntity<DepartamentoResponseDTO> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody DepartamentoRequestDTO dto) {
        return ResponseEntity.ok(departamentoService.actualizar(id, dto));
    }

    /** DELETE /api/departamentos/{id} */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        departamentoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
