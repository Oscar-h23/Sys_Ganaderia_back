package pe.idat.SistemaGanaderiaHuaman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pe.idat.SistemaGanaderiaHuaman.model.LoginRequest;
import pe.idat.SistemaGanaderiaHuaman.model.LoginResponse;
import pe.idat.SistemaGanaderiaHuaman.model.Usuario;
import pe.idat.SistemaGanaderiaHuaman.security.JwtTokenProvider;
import pe.idat.SistemaGanaderiaHuaman.service.UsuarioService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(originPatterns = "http://localhost:4200")
public class AuthController {

    // Inyección del AuthenticationManager para procesar la autenticación de usuarios.
    @Autowired
    private AuthenticationManager authenticationManager;

    // Inyección del proveedor de token JWT para generar tokens de autenticación.
    @Autowired
    private JwtTokenProvider tokenProvider;

    // Inyección del servicio de usuarios para acceder a la información del usuario.
    @Autowired
    private UsuarioService usuarioService;

    /**
     * Endpoint para autenticar al usuario.
     *
     * Recibe una petición con las credenciales (correo y contraseña),
     * realiza la autenticación y, en caso de éxito, genera y retorna un token JWT.
     *
     * @param loginRequest Objeto que contiene el correo y la contraseña del usuario.
     * @return ResponseEntity con el token JWT, nombre, id y rol del usuario autenticado,
     *         o una respuesta de error en caso de fallo en la autenticación.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        // Se crea el token de autenticación usando el correo y la contraseña recibidos
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getCorreo(), loginRequest.getPassword())
        );

        // Se establece la autenticación en el contexto de seguridad para mantener el estado del usuario
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Se genera el token JWT basado en el correo del usuario
        String token = tokenProvider.generateToken(loginRequest.getCorreo());

        // Se obtiene el usuario autenticado utilizando el servicio de usuarios
        Usuario usuario = usuarioService.findByCorreo(loginRequest.getCorreo());
        if (usuario == null) {
            // En caso de que no se encuentre el usuario, se retorna un error (400 Bad Request)
            return ResponseEntity.badRequest().build();
        }

        // Se construye y retorna la respuesta con los detalles del usuario y el token JWT
        return ResponseEntity.ok(new LoginResponse(token, usuario.getNombre(), usuario.getId(), usuario.getRol().getNombre()));
    }
}
