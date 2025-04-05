package pe.idat.SistemaGanaderiaHuaman.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Corral {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_corral")
    private Long id;

    private String nombre;
    private String tipo;
    private int capacidad;
    private int cantidadAnimales;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "establo_id")
    private Establo establo;

    @OneToMany(mappedBy = "corral", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("corral") // 🔥 Evita loops infinitos
    private List<Ganado> ganado;

    public Corral() {}

    public Corral(String nombre, String tipo, int capacidad) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.capacidad = capacidad;
        this.cantidadAnimales = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public int getCantidadAnimales() {
        return cantidadAnimales;
    }

    public void setCantidadAnimales(int cantidadAnimales) {
        this.cantidadAnimales = cantidadAnimales;
    }

    public Establo getEstablo() {
        return establo;
    }

    public void setEstablo(Establo establo) {
        this.establo = establo;
    }

    public List<Ganado> getGanado() {
        return ganado;
    }

    public void setGanado(List<Ganado> ganado) {
        this.ganado = ganado;
    }
}
