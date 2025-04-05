package pe.idat.SistemaGanaderiaHuaman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.idat.SistemaGanaderiaHuaman.model.Medicina;

@Repository
public interface MedicinaRepository extends JpaRepository<Medicina, Long> {
    // Aquí puedes agregar consultas personalizadas si es necesario
}
