package pe.idat.SistemaGanaderiaHuaman.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "alimentacion_corral")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlimentacionCorral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alimentacion_corral")
    private Long idAlimentacionCorral;

    // Codigo único para la alimentación del corral, generado automáticamente
    @Column(name = "codigo_alimentacion_corral", unique = true, nullable = false)
    private String codigoAlimentacionCorral; // Cambiado a camelCase

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_alimento", nullable = false)  // Usar un nombre distinto para la clave foránea
    private Alimento alimento;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_corral", nullable = false)  // Usar un nombre distinto para la clave foránea
    private Corral corral;

    @Column(nullable = false)
    private BigDecimal cantidad;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;


}
