package pe.idat.SistemaGanaderiaHuaman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.idat.SistemaGanaderiaHuaman.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Puedes agregar consultas personalizadas si es necesario
}
