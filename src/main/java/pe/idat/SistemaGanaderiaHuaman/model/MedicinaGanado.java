package pe.idat.SistemaGanaderiaHuaman.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;  // Importamos para generar un UUID

@Entity
@Table(name = "medicina_ganado")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicinaGanado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medicina_ganado")
    private Long idMedicinaGanado;

    // codigo unico para medicina ganada, se autogenera
    @Column(name = "codigo_medicina_ganado", unique = true, nullable = false)
    private String codigoMedicinaGanado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_medicina", nullable = false)
    private Medicina medicina;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_ganado", nullable = false)
    private Ganado ganado;

    @Column(nullable = false)
    private BigDecimal cantidad;

    @Column(name = "fecha_aplicacion", nullable = false)
    private LocalDate fechaAplicacion;

    private String observaciones;


}
