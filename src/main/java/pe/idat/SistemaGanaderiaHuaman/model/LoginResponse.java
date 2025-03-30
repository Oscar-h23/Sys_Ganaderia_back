package pe.idat.SistemaGanaderiaHuaman.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    private String accessToken;
    private final String tokenType = "Bearer";
    private String nombreUsuario;
    private Long id;
    private String role;
}
