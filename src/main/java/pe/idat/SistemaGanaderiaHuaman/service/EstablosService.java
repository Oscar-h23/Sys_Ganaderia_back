package pe.idat.SistemaGanaderiaHuaman.service;


import pe.idat.SistemaGanaderiaHuaman.repository.*;
import pe.idat.SistemaGanaderiaHuaman.model.*;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


@Service
@Transactional
public class EstablosService {

    private static final Logger logger = Logger.getLogger(EstablosService.class.getName());

    private final EstabloRepository establoRepository;
    private final CorralRepository corralRepository;
    private final GanadoRepository ganadoRepository;

    public EstablosService(EstabloRepository establoRepository,
                           CorralRepository corralRepository,
                           GanadoRepository ganadoRepository) {
        this.establoRepository = establoRepository;
        this.corralRepository = corralRepository;
        this.ganadoRepository = ganadoRepository;
    }

    // Métodos para Establos
    public List<Establo> getEstablos() {
        return establoRepository.findAll();
    }

    public Optional<Establo> getEstablo(Long id) {
        return establoRepository.findById(id);
    }

    public Establo createEstablo(Establo establo) {
        return establoRepository.save(establo);
    }

    public Establo updateEstablo(Long id, Establo updated) {
        return establoRepository.findById(id).map(e -> {
            e.setNombre(updated.getNombre());
            e.setUbicacion(updated.getUbicacion());
            e.setCapacidad(updated.getCapacidad());
            e.setArea(updated.getArea());
            return establoRepository.save(e);
        }).orElse(null);
    }

    public boolean deleteEstablo(Long id) {
        if (establoRepository.existsById(id)) {
            establoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Métodos para Corrales
    public List<Corral> getCorrales(Long establoId) {
        return corralRepository.findByEstabloId(establoId);
    }

    public List<Corral> obtenerCorralesPorEstablo(Long establoId) {
        return corralRepository.findByEstabloId(establoId);
    }
    public Optional<Corral> getCorralById(Long corralId) {
        return corralRepository.findById(corralId);
    }


    public Optional<Corral> getCorral(Long corralId) {
        return corralRepository.findById(corralId);
    }

    public Corral createCorral(Long establoId, Corral corral) {
        return establoRepository.findById(establoId).map(establo -> {
            corral.setEstablo(establo);
            return corralRepository.save(corral);
        }).orElse(null);
    }

    public boolean deleteCorral(Long corralId) {
        if (corralRepository.existsById(corralId)) {
            corralRepository.deleteById(corralId);
            return true;
        }
        return false;
    }

}