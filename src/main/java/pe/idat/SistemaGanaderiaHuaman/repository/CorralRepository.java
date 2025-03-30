package pe.idat.SistemaGanaderiaHuaman.repository;

import pe.idat.SistemaGanaderiaHuaman.model.Corral;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CorralRepository extends JpaRepository<Corral, Long> {
    List<Corral> findByEstabloId(Long establoId);
}