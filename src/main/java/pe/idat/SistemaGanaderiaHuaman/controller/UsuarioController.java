package pe.idat.SistemaGanaderiaHuaman.controller;

import pe.idat.SistemaGanaderiaHuaman.model.Usuario;
import pe.idat.SistemaGanaderiaHuaman.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(originPatterns = "http://localhost:4200")
public class UsuarioController {

    // Inyección del servicio de usuarios para acceder a la lógica de negocio
    @Autowired
    private UsuarioService usuarioService;

    /**
     * Registra un nuevo usuario.
     * Se verifica que el correo no exista previamente para evitar duplicados.
     *
     * @param usuario Objeto Usuario recibido en el cuerpo de la petición.
     * @return ResponseEntity con el usuario registrado o error de petición.
     */
    @PostMapping("/register")
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody Usuario usuario) {
        // Verifica si ya existe un usuario con el mismo correo
        if (usuarioService.findByCorreo(usuario.getCorreo()) != null) {
            return ResponseEntity.badRequest().build(); // Retorna error si el correo ya está en uso
        }
        // Registra el nuevo usuario (se encripta la contraseña internamente)
        Usuario nuevoUsuario = usuarioService.registrarUsuario(usuario);
        return ResponseEntity.ok(nuevoUsuario);
    }

    /**
     * Lista todos los usuarios.
     *
     * @return ResponseEntity con la lista de usuarios.
     */
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        // Obtiene la lista completa de usuarios del servicio
        return ResponseEntity.ok(usuarioService.findAll());
    }

    /**
     * Busca un usuario por su ID.
     *
     * @param id ID del usuario a buscar.
     * @return ResponseEntity con el usuario encontrado o un error 404 si no se encuentra.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.findById(id);
        // Retorna 200 OK si se encuentra el usuario, o 404 Not Found si no existe
        return usuario != null ? ResponseEntity.ok(usuario) : ResponseEntity.notFound().build();
    }

    /**
     * Busca usuarios por nombre (búsqueda parcial e insensible a mayúsculas/minúsculas).
     *
     * @param nombre Nombre (o parte del mismo) del usuario a buscar.
     * @return ResponseEntity con la lista de usuarios encontrados o error 404 si la lista está vacía.
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<Usuario>> buscarPorNombre(@RequestParam String nombre) {
        List<Usuario> usuarios = usuarioService.findByNombre(nombre);
        // Retorna 200 OK si se encontraron usuarios, o 404 Not Found si no hay resultados
        return usuarios.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(usuarios);
    }

    /**
     * Actualiza la información de un usuario existente.
     *
     * @param id                ID del usuario a actualizar.
     * @param usuarioActualizado Objeto con los datos actualizados del usuario.
     * @return ResponseEntity con el usuario actualizado o error 404 si no se encuentra el usuario.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        Usuario usuario = usuarioService.findById(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        // Actualiza los campos del usuario con los datos proporcionados
        usuario.setNombre(usuarioActualizado.getNombre());
        usuario.setCorreo(usuarioActualizado.getCorreo());
        usuario.setTelefono(usuarioActualizado.getTelefono());
        usuario.setDireccion(usuarioActualizado.getDireccion());
        usuario.setRol(usuarioActualizado.getRol());
        usuario.setEstado(usuarioActualizado.getEstado());

        // Guarda y retorna el usuario actualizado
        Usuario usuarioGuardado = usuarioService.save(usuario);
        return ResponseEntity.ok(usuarioGuardado);
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param id ID del usuario a eliminar.
     * @return ResponseEntity sin contenido si la eliminación es exitosa o error 404 si no se encuentra el usuario.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioService.findById(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        // Elimina el usuario del repositorio
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
