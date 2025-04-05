package pe.idat.SistemaGanaderiaHuaman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.idat.SistemaGanaderiaHuaman.model.Alimento;

import java.util.List;

@Repository
public interface AlimentoRepository extends JpaRepository<Alimento, Integer> {
    // Puedes agregar consultas personalizadas si lo necesitas, por ejemplo:
    List<Alimento> findByProveedorEmpresa(String empresa);
    List<Alimento> findByTipo(String tipo);

}
