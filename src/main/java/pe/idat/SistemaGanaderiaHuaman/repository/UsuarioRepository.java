package pe.idat.SistemaGanaderiaHuaman.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.idat.SistemaGanaderiaHuaman.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);

    Page<Usuario> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);


}