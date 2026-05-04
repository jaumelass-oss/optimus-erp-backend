package optimus_erp.controller;

import optimus_erp.model.Activo;
import optimus_erp.repository.ActivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RequestMapping("/api/activos")
@RestController
public class ActivoController {

    @Autowired
    private ActivoRepository activoRepository;

    @GetMapping
    public List<Activo> listarTodos() {
        return activoRepository.findAll();
    }

    @PostMapping
    public Activo crear(@RequestBody Activo activo) {
        return activoRepository.save(activo);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        activoRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Activo> actualizarActivo(@PathVariable Long id, @RequestBody Activo activoDetalles) {
        return activoRepository.findById(id)
                .map(activo -> {
                    activo.setStock(activoDetalles.getStock());
                    Activo actualizado = activoRepository.save(activo);
                    return ResponseEntity.ok(actualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
