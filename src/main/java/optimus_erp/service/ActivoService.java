package optimus_erp.service;

import optimus_erp.dto.ActivoRequestDTO;
import optimus_erp.dto.ActivoResponseDTO;
import optimus_erp.model.Activo;
import optimus_erp.model.Cliente;
import optimus_erp.repository.ActivoRepository;
import optimus_erp.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ActivoService {

    private final ActivoRepository  activoRepository;
    private final ClienteRepository clienteRepository;

    public ActivoService(ActivoRepository activoRepository,
                         ClienteRepository clienteRepository) {
        this.activoRepository  = activoRepository;
        this.clienteRepository = clienteRepository;
    }

    @Transactional(readOnly = true)
    public List<ActivoResponseDTO> listarTodos() {
        return activoRepository.findAll()
                .stream().map(ActivoResponseDTO::desde)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ActivoResponseDTO buscarPorId(Long id) {
        return activoRepository.findById(id)
                .map(ActivoResponseDTO::desde)
                .orElseThrow(() -> new RuntimeException("Activo no encontrado: " + id));
    }

    public ActivoResponseDTO crear(ActivoRequestDTO dto) {
        Activo a = new Activo();
        mapearDatos(a, dto);
        // Si no viene fecha de entrada, se pone hoy automáticamente
        if (a.getFechaEntrada() == null) {
            a.setFechaEntrada(LocalDate.now());
        }
        return ActivoResponseDTO.desde(activoRepository.save(a));
    }

    public ActivoResponseDTO actualizar(Long id, ActivoRequestDTO dto) {
        Activo a = activoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activo no encontrado: " + id));
        mapearDatos(a, dto);
        return ActivoResponseDTO.desde(activoRepository.save(a));
    }

    public void eliminar(Long id) {
        if (!activoRepository.existsById(id)) {
            throw new RuntimeException("Activo no encontrado: " + id);
        }
        activoRepository.deleteById(id);
    }

    // ── Privado ──────────────────────────────────────────────────────────────

    private void mapearDatos(Activo a, ActivoRequestDTO dto) {
        a.setNombre(dto.getNombre());
        a.setNumeroSerie(dto.getNumeroSerie());
        a.setTipo(dto.getTipo());
        a.setValor(dto.getValor());
        a.setStock(dto.getStock());
        a.setEmpleadoId(dto.getEmpleadoId());
        a.setFechaEntrada(dto.getFechaEntrada());
        a.setFechaVenta(dto.getFechaVenta());
        a.setNotas(dto.getNotas());

        if (dto.getClienteVendidoId() != null) {
            Cliente c = clienteRepository.findById(dto.getClienteVendidoId())
                    .orElseThrow(() -> new RuntimeException(
                            "Cliente no encontrado: " + dto.getClienteVendidoId()));
            a.setClienteVendido(c);
        } else {
            a.setClienteVendido(null);
        }
    }
}