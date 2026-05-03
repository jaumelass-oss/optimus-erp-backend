package optimus_erp.service;

import optimus_erp.dto.FacturaRequestDTO;
import optimus_erp.dto.FacturaResponseDTO;
import optimus_erp.model.*;
import optimus_erp.repository.ActivoRepository;
import optimus_erp.repository.ClienteRepository;
import optimus_erp.repository.FacturaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FacturaService {

    private final FacturaRepository  facturaRepository;
    private final ClienteRepository  clienteRepository;
    private final ActivoRepository   activoRepository;

    public FacturaService(FacturaRepository facturaRepository,
                          ClienteRepository clienteRepository,
                          ActivoRepository activoRepository) {
        this.facturaRepository = facturaRepository;
        this.clienteRepository = clienteRepository;
        this.activoRepository  = activoRepository;
    }

    // ── Consultas ────────────────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public List<FacturaResponseDTO> listarTodas() {
        return facturaRepository.findAll()
                .stream().map(FacturaResponseDTO::desde)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public FacturaResponseDTO buscarPorId(Integer id) {
        return facturaRepository.findById(id)
                .map(FacturaResponseDTO::desde)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada: " + id));
    }

    @Transactional(readOnly = true)
    public List<FacturaResponseDTO> listarPorCliente(Integer clienteId) {
        return facturaRepository.findByClienteId(clienteId)
                .stream().map(FacturaResponseDTO::desde)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<FacturaResponseDTO> listarPorEstado(EstadoFactura estado) {
        return facturaRepository.findByEstado(estado)
                .stream().map(FacturaResponseDTO::desde)
                .collect(Collectors.toList());
    }

    // ── Crear ────────────────────────────────────────────────────────────────

    public FacturaResponseDTO crear(FacturaRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado: " + dto.getClienteId()));

        Factura factura = new Factura();
        factura.setNumeroFactura(generarNumero());
        factura.setCliente(cliente);
        factura.setFechaEmision(dto.getFechaEmision());
        factura.setIvaPorcentaje(dto.getIvaPorcentaje());
        factura.setEstado(EstadoFactura.BORRADOR);

        dto.getLineas().forEach(ldto -> {
            Activo activo = null;
            if (ldto.getActivoId() != null) {
                activo = activoRepository.findById(ldto.getActivoId()).orElse(null);
            }
            LineaFactura linea = new LineaFactura(
                    ldto.getDescripcion(),
                    ldto.getCantidad(),
                    ldto.getPrecioUnitario(),
                    activo
            );
            factura.agregarLinea(linea);
        });

        return FacturaResponseDTO.desde(facturaRepository.save(factura));
    }

    // ── Editar ───────────────────────────────────────────────────────────────

    public FacturaResponseDTO actualizar(Integer id, FacturaRequestDTO dto) {
        Factura factura = facturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada: " + id));

        if (factura.getEstado() != EstadoFactura.BORRADOR) {
            throw new RuntimeException(
                    "Solo se pueden editar facturas en BORRADOR. Estado actual: " + factura.getEstado()
            );
        }

        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado: " + dto.getClienteId()));

        factura.setCliente(cliente);
        factura.setFechaEmision(dto.getFechaEmision());
        factura.setIvaPorcentaje(dto.getIvaPorcentaje());

        factura.getLineas().clear();
        dto.getLineas().forEach(ldto -> {
            Activo activo = null;
            if (ldto.getActivoId() != null) {
                activo = activoRepository.findById(ldto.getActivoId()).orElse(null);
            }
            LineaFactura linea = new LineaFactura(
                    ldto.getDescripcion(),
                    ldto.getCantidad(),
                    ldto.getPrecioUnitario(),
                    activo
            );
            factura.agregarLinea(linea);
        });

        return FacturaResponseDTO.desde(facturaRepository.save(factura));
    }

    // ── Cambio de estado ─────────────────────────────────────────────────────

    /**
     * Transiciones permitidas:
     *   BORRADOR  → EMITIDA  → PAGADA
     *   BORRADOR  → CANCELADA
     *   EMITIDA   → CANCELADA
     */
    public FacturaResponseDTO cambiarEstado(Integer id, EstadoFactura nuevoEstado) {
        Factura factura = facturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada: " + id));

        validarTransicion(factura.getEstado(), nuevoEstado);
        factura.setEstado(nuevoEstado);

        return FacturaResponseDTO.desde(facturaRepository.save(factura));
    }

    private void validarTransicion(EstadoFactura actual, EstadoFactura siguiente) {
        boolean valida = switch (actual) {
            case BORRADOR  -> siguiente == EstadoFactura.EMITIDA   || siguiente == EstadoFactura.CANCELADA;
            case EMITIDA   -> siguiente == EstadoFactura.PAGADA    || siguiente == EstadoFactura.CANCELADA;
            case PAGADA    -> false;
            case CANCELADA -> false;
        };
        if (!valida) {
            throw new RuntimeException("Transición inválida: " + actual + " → " + siguiente);
        }
    }

    // ── Eliminar ─────────────────────────────────────────────────────────────

    public void eliminar(Integer id) {
        Factura factura = facturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada: " + id));
        if (factura.getEstado() != EstadoFactura.BORRADOR) {
            throw new RuntimeException("Solo se pueden eliminar facturas en estado BORRADOR.");
        }
        facturaRepository.deleteById(id);
    }

    // ── Numeración automática ────────────────────────────────────────────────

    /**
     * Genera número único: FAC-2026-0001, FAC-2026-0002, ...
     * El año se toma de la fecha actual del servidor.
     */
    private String generarNumero() {
        int anio = LocalDate.now().getYear();
        String prefijo = "FAC-" + anio + "-";

        int siguiente = facturaRepository
                .findUltimoNumeroPorPrefijo(prefijo)
                .map(ultimo -> Integer.parseInt(ultimo.replace(prefijo, "")) + 1)
                .orElse(1);

        return prefijo + String.format("%04d", siguiente);
    }
}