package pe.idat.SistemaGanaderiaHuaman.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.idat.SistemaGanaderiaHuaman.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
    Optional<Proveedor> findByCorreo(String correo);
    Optional<Proveedor> findByEmpresa(String empresa);
    List<Proveedor> findByEmpresaContainingIgnoreCase(String empresa);
}
