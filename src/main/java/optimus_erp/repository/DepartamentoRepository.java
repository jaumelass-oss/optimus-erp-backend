package optimus_erp.repository;

import optimus_erp.model.Departamento;
import optimus_erp.model.Sede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Integer> {

    List<Departamento> findBySede(Sede sede);

    List<Departamento> findByNombreContainingIgnoreCase(String nombre);

    boolean existsByNombre(String nombre);
}