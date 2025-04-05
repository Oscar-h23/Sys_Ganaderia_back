package pe.idat.SistemaGanaderiaHuaman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.idat.SistemaGanaderiaHuaman.model.Medicina;
import pe.idat.SistemaGanaderiaHuaman.service.MedicinaService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/medicinas")
public class MedicinaController {

    @Autowired
    private MedicinaService medicinaService;

    @GetMapping
    public List<Medicina> obtenerTodos() {
        return medicinaService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medicina> obtenerPorId(@PathVariable("id") Long id) {
        Optional<Medicina> medicina = medicinaService.obtenerPorId(id);
        return medicina.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Medicina> guardar(@RequestBody Medicina medicina) {
        Medicina nuevaMedicina = medicinaService.guardar(medicina);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaMedicina);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medicina> actualizar(@PathVariable("id") Long id, @RequestBody Medicina medicina) {
        if (!medicinaService.obtenerPorId(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        medicina.setIdMedicina(id);
        Medicina actualizada = medicinaService.guardar(medicina);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Long id) {
        if (!medicinaService.obtenerPorId(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        medicinaService.eliminar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
