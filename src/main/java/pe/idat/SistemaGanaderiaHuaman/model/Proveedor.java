package pe.idat.SistemaGanaderiaHuaman.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "proveedor")
@Getter
@Setter
@NoArgsConstructor
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proveedor")
    private Long id;

    @Column(nullable = false, unique = true, length = 11)
    private String ruc;

    @Column(nullable = false, length = 100)
    private String empresa;

    @Column(nullable = false, length = 100)
    private String representante;

    @Column(nullable = false, length = 20)
    private String telefono;

    @Column(nullable = false, unique = true, length = 100)
    private String correo;

    @Column(nullable = false)
    private Boolean estado;

    public Proveedor(String ruc, String empresa, String representante, String telefono, String correo, Boolean estado) {
        this.ruc = ruc;
        this.empresa = empresa;
        this.representante = representante;
        this.telefono = telefono;
        this.correo = correo;
        this.estado = estado;
    }


}
