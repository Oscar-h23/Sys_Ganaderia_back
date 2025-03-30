package pe.idat.SistemaGanaderiaHuaman.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Establo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String ubicacion;
    private int capacidad;
    private double area;

    @JsonManagedReference
    @OneToMany(mappedBy = "establo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Corral> corrales = new ArrayList<>();

    public Establo(String nombre, String ubicacion, int capacidad, double area) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.capacidad = capacidad;
        this.area = area;
    }
}
