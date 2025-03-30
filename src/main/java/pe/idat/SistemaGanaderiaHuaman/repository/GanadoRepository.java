package pe.idat.SistemaGanaderiaHuaman.repository;

import pe.idat.SistemaGanaderiaHuaman.model.Ganado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GanadoRepository extends JpaRepository<Ganado, Long> {
    List<Ganado> findByCorralId(Long corralId);
    Optional<Ganado> findByCodigoUnico(String codigoUnico);
    List<Ganado> findAllByOrderByTiempoDesc();
    List<Ganado> findByCorralEstabloId(Long establoId);



}