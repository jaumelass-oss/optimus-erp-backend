package optimus_erp.repository;

import optimus_erp.model.LineaFactura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LineaFacturaRepository extends JpaRepository<LineaFactura, Integer> {

    List<LineaFactura> findByFacturaId(Integer facturaId);

    boolean existsByActivoId(Long activoId);
}