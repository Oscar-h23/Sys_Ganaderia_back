package pe.idat.SistemaGanaderiaHuaman.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "medicina")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Medicina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medicina")
    private Long idMedicina;

    private String nombre;

    @Column(name = "tipo")
    private String tipo;  // Cambié el tipo a String

    private String descripcion;

    @Column(name = "dosis_recomendada")
    private String dosisRecomendada;

    private BigDecimal cantidad;

    private BigDecimal precio;

    @Column(name = "fecha_registro", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDate fechaRegistro;

    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;
}