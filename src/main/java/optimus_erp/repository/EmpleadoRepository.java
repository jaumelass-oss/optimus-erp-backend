package optimus_erp.repository;

import optimus_erp.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {

    List<Empleado> findByDepartamentoId(Integer departamentoId);

    List<Empleado> findByNombreContainingIgnoreCaseOrApellidosContainingIgnoreCase(
            String nombre, String apellidos);

    Optional<Empleado> findByEmailCorporativo(String email);

    boolean existsByEmailCorporativo(String email);
}
