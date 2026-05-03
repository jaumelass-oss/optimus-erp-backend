package optimus_erp.repository;

import optimus_erp.model.EstadoFactura;
import optimus_erp.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Integer> {

    List<Factura> findByClienteId(Integer clienteId);

    List<Factura> findByEstado(EstadoFactura estado);

    Optional<Factura> findByNumeroFactura(String numeroFactura);

    @Query("SELECT f.numeroFactura FROM Factura f " +
            "WHERE f.numeroFactura LIKE :prefix% " +
            "ORDER BY f.numeroFactura DESC " +
            "LIMIT 1")
    Optional<String> findUltimoNumeroPorPrefijo(@Param("prefix") String prefix);
}
