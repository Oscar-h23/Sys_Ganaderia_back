package pe.idat.SistemaGanaderiaHuaman.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Ganado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String codigoUnico; // Ahora es ingresado por el usuario

    private int tiempo; // en días
    private String raza;
    private double peso;

    @ManyToOne(fetch = FetchType.EAGER) // ⚠️ EAGER para cargar siempre el corral
    @JoinColumn(name = "corral_id")
    @JsonIgnoreProperties("ganado") // 🔥 Evita loops infinitos
    private Corral corral;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm dd/MM/yyyy", timezone = "America/Lima")
    private Date fechaRegistro;

    public Ganado() {}

    public Ganado(String codigoUnico, String raza, double peso) {
        this.codigoUnico = codigoUnico;
        this.tiempo = 1; // Inicia en 1 día
        this.raza = raza;
        this.peso = peso;
    }

    @PrePersist
    protected void onCreate() {
        this.fechaRegistro = new Date();
        this.tiempo = 1; // Se asegura que inicie en 1
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }
    //set id
    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoUnico() {
        return codigoUnico;
    }

    public void setCodigoUnico(String codigoUnico) {
        this.codigoUnico = codigoUnico;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }


    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public String getRaza() {
        return raza;
    }

    public double getPeso() {
        return peso;
    }

    public Corral getCorral() {
        return corral;
    }

    public void setCorral(Corral corral) {
        this.corral = corral;
    }


    public Date getFechaRegistro() {
        return fechaRegistro;
    }
}