package pe.idat.SistemaGanaderiaHuaman.controller;

import pe.idat.SistemaGanaderiaHuaman.model.Usuario;
import pe.idat.SistemaGanaderiaHuaman.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(originPatterns = "http://localhost:4200")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * 📌 Registra un nuevo usuario.
     * @param usuario Objeto Usuario recibido en el cuerpo de la petición.
     * @return ResponseEntity con el usuario registrado o error de petición.
     */
    @PostMapping("/register")
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody Usuario usuario) {
        if (usuarioService.findByCorreo(usuario.getCorreo()) != null) {
            return ResponseEntity.badRequest().build(); // Si el correo ya existe, retorna error
        }
        Usuario nuevoUsuario = usuarioService.registrarUsuario(usuario);
        return ResponseEntity.ok(nuevoUsuario);
    }

    /**
     * 📌 Lista todos los usuarios de forma paginada.
     * @param page Número de página (por defecto 0).
     * @param size Tamaño de la página (por defecto 10).
     * @return ResponseEntity con la página de usuarios.
     */
    @GetMapping
    public ResponseEntity<Page<Usuario>> listarUsuarios(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Usuario> usuarios = usuarioService.findAll(PageRequest.of(page, size));
        return ResponseEntity.ok(usuarios);
    }

    /**
     * 📌 Busca un usuario por su ID.
     * @param id ID del usuario a buscar.
     * @return ResponseEntity con el usuario encontrado o un error 404.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.findById(id);
        return usuario != null ? ResponseEntity.ok(usuario) : ResponseEntity.notFound().build();
    }

    /**
     * 📌 Busca usuarios por nombre de forma paginada.
     * @param nombre Nombre del usuario.
     * @param page Número de página (por defecto 0).
     * @param size Tamaño de la página (por defecto 10).
     * @return ResponseEntity con la página de usuarios encontrados.
     */
    @GetMapping("/buscar")
    public ResponseEntity<Page<Usuario>> buscarPorNombre(
            @RequestParam String nombre,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Usuario> usuarios = usuarioService.findByNombre(nombre, PageRequest.of(page, size));
        return ResponseEntity.ok(usuarios);
    }

    /**
     * 📌 Actualiza la información de un usuario existente.
     * @param id ID del usuario a actualizar.
     * @param usuarioActualizado Objeto con los datos actualizados.
     * @return ResponseEntity con el usuario actualizado o error 404.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        Usuario usuario = usuarioService.findById(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        usuario.setNombre(usuarioActualizado.getNombre());
        usuario.setCorreo(usuarioActualizado.getCorreo());
        usuario.setTelefono(usuarioActualizado.getTelefono());
        usuario.setDireccion(usuarioActualizado.getDireccion());
        usuario.setRol(usuarioActualizado.getRol());
        usuario.setEstado(usuarioActualizado.getEstado());

        Usuario usuarioGuardado = usuarioService.save(usuario);
        return ResponseEntity.ok(usuarioGuardado);
    }

    /**
     * 📌 Elimina un usuario por su ID.
     * @param id ID del usuario a eliminar.
     * @return ResponseEntity sin contenido si la eliminación es exitosa o error 404.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioService.findById(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
