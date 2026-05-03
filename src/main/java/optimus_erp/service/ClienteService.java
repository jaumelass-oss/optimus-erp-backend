package optimus_erp.service;

import optimus_erp.dto.ClienteRequestDTO;
import optimus_erp.dto.ClienteResponseDTO;
import optimus_erp.model.Cliente;
import optimus_erp.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> listarTodos() {
        return clienteRepository.findAll()
                .stream().map(ClienteResponseDTO::desde)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ClienteResponseDTO buscarPorId(Integer id) {
        return clienteRepository.findById(id)
                .map(ClienteResponseDTO::desde)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado: " + id));
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> buscarPorNombre(String razonSocial) {
        return clienteRepository.findByRazonSocialContainingIgnoreCase(razonSocial)
                .stream().map(ClienteResponseDTO::desde)
                .collect(Collectors.toList());
    }

    public ClienteResponseDTO crear(ClienteRequestDTO dto) {
        if (clienteRepository.existsByCifVies(dto.getCifVies())) {
            throw new RuntimeException("Ya existe un cliente con CIF/VIES: " + dto.getCifVies());
        }
        Cliente cliente = new Cliente();
        cliente.setRazonSocial(dto.getRazonSocial());
        cliente.setCifVies(dto.getCifVies());
        cliente.setPais(dto.getPais() != null ? dto.getPais() : "España");
        cliente.setEmailFacturacion(dto.getEmailFacturacion());
        return ClienteResponseDTO.desde(clienteRepository.save(cliente));
    }

    public ClienteResponseDTO actualizar(Integer id, ClienteRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado: " + id));
        cliente.setRazonSocial(dto.getRazonSocial());
        cliente.setCifVies(dto.getCifVies());
        cliente.setPais(dto.getPais());
        cliente.setEmailFacturacion(dto.getEmailFacturacion());
        return ClienteResponseDTO.desde(clienteRepository.save(cliente));
    }

    public void eliminar(Integer id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente no encontrado: " + id);
        }
        clienteRepository.deleteById(id);
    }
}