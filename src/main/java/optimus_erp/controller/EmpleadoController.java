package optimus_erp.controller;

import jakarta.validation.Valid;
import optimus_erp.dto.EmpleadoRequestDTO;
import optimus_erp.dto.EmpleadoResponseDTO;
import optimus_erp.service.EmpleadoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
@CrossOrigin(origins = "*")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    /** GET /api/empleados                        → todos */
    /** GET /api/empleados?buscar=Ana             → por nombre o apellido */
    /** GET /api/empleados?departamentoId=2       → por departamento */
    @GetMapping
    public ResponseEntity<List<EmpleadoResponseDTO>> listar(
            @RequestParam(required = false) String buscar,
            @RequestParam(required = false) Integer departamentoId) {

        List<EmpleadoResponseDTO> resultado;
        if (buscar != null && !buscar.isBlank()) {
            resultado = empleadoService.buscarPorNombre(buscar);
        } else if (departamentoId != null) {
            resultado = empleadoService.listarPorDepartamento(departamentoId);
        } else {
            resultado = empleadoService.listarTodos();
        }
        return ResponseEntity.ok(resultado);
    }

    /** GET /api/empleados/{id} */
    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(empleadoService.buscarPorId(id));
    }

    /** POST /api/empleados */
    @PostMapping
    public ResponseEntity<EmpleadoResponseDTO> crear(
            @Valid @RequestBody EmpleadoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(empleadoService.crear(dto));
    }

    /** PUT /api/empleados/{id} */
    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDTO> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody EmpleadoRequestDTO dto) {
        return ResponseEntity.ok(empleadoService.actualizar(id, dto));
    }

    /** DELETE /api/empleados/{id} */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        empleadoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
