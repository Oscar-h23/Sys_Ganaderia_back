package pe.idat.SistemaGanaderiaHuaman.model;

import lombok.Data;

@Data
public class LoginRequest {
    private String correo;
    private String password;
}
