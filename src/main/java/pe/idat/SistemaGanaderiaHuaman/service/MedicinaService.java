package pe.idat.SistemaGanaderiaHuaman.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.idat.SistemaGanaderiaHuaman.model.Medicina;
import pe.idat.SistemaGanaderiaHuaman.repository.MedicinaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MedicinaService {

    @Autowired
    private MedicinaRepository medicinaRepository;

    public List<Medicina> obtenerTodos() {
        return medicinaRepository.findAll();
    }

    public Optional<Medicina> obtenerPorId(Long id) {
        return medicinaRepository.findById(id);
    }

    public Medicina guardar(Medicina medicina) {
        return medicinaRepository.save(medicina);
    }

    public void eliminar(Long id) {
        medicinaRepository.deleteById(id);
    }
}