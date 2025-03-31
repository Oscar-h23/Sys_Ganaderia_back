package pe.idat.SistemaGanaderiaHuaman.service;

import pe.idat.SistemaGanaderiaHuaman.model.Usuario;
import pe.idat.SistemaGanaderiaHuaman.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 📌 Retorna una lista paginada con todos los usuarios.
     *
     * @param pageable Objeto para manejar la paginación.
     * @return Página de Usuarios.
     */
    public Page<Usuario> findAll(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    /**
     * 📌 Busca un usuario por su ID.
     *
     * @param id Identificador del usuario.
     * @return Usuario si se encuentra, o null en caso contrario.
     */
    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    /**
     * 📌 Guarda o actualiza un usuario en la base de datos.
     *
     * @param usuario Objeto Usuario a guardar.
     * @return Usuario guardado.
     */
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    /**
     * 📌 Elimina un usuario dado su ID.
     *
     * @param id Identificador del usuario a eliminar.
     */
    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }

    /**
     * 📌 Busca un usuario por su correo electrónico.
     *
     * @param correo Correo del usuario.
     * @return Usuario si se encuentra, o null en caso contrario.
     */
    public Usuario findByCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo).orElse(null);
    }

    /**
     * 📌 Registra un usuario en la base de datos con la contraseña encriptada.
     *
     * @param usuario Objeto Usuario a registrar.
     * @return Usuario registrado.
     */
    public Usuario registrarUsuario(Usuario usuario) {
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        return usuarioRepository.save(usuario);
    }

    /**
     * 📌 Busca usuarios por el nombre con paginación.
     *
     * @param nombre Nombre del usuario.
     * @param pageable Objeto para manejar la paginación.
     * @return Página de Usuarios encontrados.
     */
    public Page<Usuario> findByNombre(String nombre, Pageable pageable) {
        return usuarioRepository.findByNombreContainingIgnoreCase(nombre, pageable);
    }
}
