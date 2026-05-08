package optimus_erp.service;

import optimus_erp.dto.EmpleadoRequestDTO;
import optimus_erp.dto.EmpleadoResponseDTO;
import optimus_erp.model.Departamento;
import optimus_erp.model.Empleado;
import optimus_erp.repository.DepartamentoRepository;
import optimus_erp.repository.EmpleadoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmpleadoService {

    private final EmpleadoRepository    empleadoRepository;
    private final DepartamentoRepository departamentoRepository;

    public EmpleadoService(EmpleadoRepository empleadoRepository,
                           DepartamentoRepository departamentoRepository) {
        this.empleadoRepository    = empleadoRepository;
        this.departamentoRepository = departamentoRepository;
    }

    @Transactional(readOnly = true)
    public List<EmpleadoResponseDTO> listarTodos() {
        return empleadoRepository.findAll()
                .stream().map(EmpleadoResponseDTO::desde)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EmpleadoResponseDTO buscarPorId(Integer id) {
        return empleadoRepository.findById(id)
                .map(EmpleadoResponseDTO::desde)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado: " + id));
    }

    @Transactional(readOnly = true)
    public List<EmpleadoResponseDTO> buscarPorNombre(String termino) {
        return empleadoRepository
                .findByNombreContainingIgnoreCaseOrApellidosContainingIgnoreCase(termino, termino)
                .stream().map(EmpleadoResponseDTO::desde)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EmpleadoResponseDTO> listarPorDepartamento(Integer departamentoId) {
        return empleadoRepository.findByDepartamentoId(departamentoId)
                .stream().map(EmpleadoResponseDTO::desde)
                .collect(Collectors.toList());
    }

    public EmpleadoResponseDTO crear(EmpleadoRequestDTO dto) {
        if (dto.getEmailCorporativo() != null &&
                empleadoRepository.existsByEmailCorporativo(dto.getEmailCorporativo())) {
            throw new RuntimeException("Ya existe un empleado con ese email corporativo.");
        }

        Empleado e = new Empleado();
        mapearDatos(e, dto);
        return EmpleadoResponseDTO.desde(empleadoRepository.save(e));
    }

    public EmpleadoResponseDTO actualizar(Integer id, EmpleadoRequestDTO dto) {
        Empleado e = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado: " + id));
        mapearDatos(e, dto);
        return EmpleadoResponseDTO.desde(empleadoRepository.save(e));
    }

    public void eliminar(Integer id) {
        if (!empleadoRepository.existsById(id)) {
            throw new RuntimeException("Empleado no encontrado: " + id);
        }
        empleadoRepository.deleteById(id);
    }


    private void mapearDatos(Empleado e, EmpleadoRequestDTO dto) {
        e.setNombre(dto.getNombre());
        e.setApellidos(dto.getApellidos());
        e.setEmailCorporativo(dto.getEmailCorporativo());
        e.setTarifaHora(dto.getTarifaHora());

        if (dto.getDepartamentoId() != null) {
            Departamento dep = departamentoRepository.findById(dto.getDepartamentoId())
                    .orElseThrow(() -> new RuntimeException(
                            "Departamento no encontrado: " + dto.getDepartamentoId()));
            e.setDepartamento(dep);
        } else {
            e.setDepartamento(null);
        }
    }
}