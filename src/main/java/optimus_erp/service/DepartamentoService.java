package optimus_erp.service;

import optimus_erp.dto.DepartamentoRequestDTO;
import optimus_erp.dto.DepartamentoResponseDTO;
import optimus_erp.model.Departamento;
import optimus_erp.repository.DepartamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DepartamentoService {

    private final DepartamentoRepository departamentoRepository;

    public DepartamentoService(DepartamentoRepository departamentoRepository) {
        this.departamentoRepository = departamentoRepository;
    }

    @Transactional(readOnly = true)
    public List<DepartamentoResponseDTO> listarTodos() {
        return departamentoRepository.findAll()
                .stream().map(DepartamentoResponseDTO::desde)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DepartamentoResponseDTO buscarPorId(Integer id) {
        return departamentoRepository.findById(id)
                .map(DepartamentoResponseDTO::desde)
                .orElseThrow(() -> new RuntimeException("Departamento no encontrado: " + id));
    }

    public DepartamentoResponseDTO crear(DepartamentoRequestDTO dto) {
        Departamento d = new Departamento();
        d.setNombre(dto.getNombre());
        d.setSede(dto.getSede());
        return DepartamentoResponseDTO.desde(departamentoRepository.save(d));
    }

    public DepartamentoResponseDTO actualizar(Integer id, DepartamentoRequestDTO dto) {
        Departamento d = departamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Departamento no encontrado: " + id));
        d.setNombre(dto.getNombre());
        d.setSede(dto.getSede());
        return DepartamentoResponseDTO.desde(departamentoRepository.save(d));
    }

    public void eliminar(Integer id) {
        Departamento d = departamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Departamento no encontrado: " + id));
        if (!d.getEmpleados().isEmpty()) {
            throw new RuntimeException(
                    "No se puede eliminar: el departamento tiene " + d.getEmpleados().size() + " empleado(s) asignado(s)."
            );
        }
        departamentoRepository.deleteById(id);
    }
}
