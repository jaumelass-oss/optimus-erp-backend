package optimus_erp.repository;

import optimus_erp.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    Optional<Cliente> findByCifVies(String cifVies);

    List<Cliente> findByRazonSocialContainingIgnoreCase(String razonSocial);

    boolean existsByCifVies(String cifVies);
}