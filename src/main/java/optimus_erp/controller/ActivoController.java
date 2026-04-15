package optimus_erp.controller;

import optimus_erp.model.Activo;
import optimus_erp.repository.ActivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@CrossOrigin(origins = "http://localhost:4200")
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
}
