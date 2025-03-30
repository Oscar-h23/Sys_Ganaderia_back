package pe.idat.SistemaGanaderiaHuaman.controller;

import pe.idat.SistemaGanaderiaHuaman.model.Ganado;
import pe.idat.SistemaGanaderiaHuaman.model.Corral;
import pe.idat.SistemaGanaderiaHuaman.service.EstablosService;
import pe.idat.SistemaGanaderiaHuaman.service.GanadoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ganado")
@CrossOrigin("*") // Permite peticiones desde el frontend
public class GanadoController {

    private final GanadoService ganadoService;
    private final EstablosService establosService; // ✅ Nombre correcto

    public GanadoController(GanadoService ganadoService, EstablosService establosService) { // ✅ Cambio aquí
        this.ganadoService = ganadoService;
        this.establosService = establosService;
    }

    // ✅ Obtener todo el ganado
    @GetMapping
    public ResponseEntity<List<Ganado>> getGanado() {
        List<Ganado> ganadoList = ganadoService.getGanados();
        return ResponseEntity.ok(ganadoList);
    }

    // ✅ Obtener ganado por corral
    @GetMapping("/corral/{corralId}")
    public ResponseEntity<List<Ganado>> getGanadoByCorral(@PathVariable Long corralId) {
        List<Ganado> ganadoList = ganadoService.getGanadoByCorral(corralId);
        return ResponseEntity.ok(ganadoList);
    }

    // ✅ Obtener ganado por código único
    @GetMapping("/codigo/{codigoUnico}")
    public ResponseEntity<Ganado> getGanadoByCodigo(@PathVariable String codigoUnico) {
        Optional<Ganado> ganado = ganadoService.getGanadoByCodigo(codigoUnico);
        return ganado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ Crear un nuevo ganado (corregido)
    @PostMapping("/{corralId}")
    public ResponseEntity<?> createGanado(@PathVariable Long corralId, @RequestBody Ganado ganado) {
        Optional<Corral> corral = establosService.getCorralById(corralId); // ✅ Llamada correcta al método

        if (corral.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: El corral con ID " + corralId + " no existe.");
        }

        ganado.setCorral(corral.get()); // ✅ Asigna el corral al ganado

        try {
            Ganado newGanado = ganadoService.createGanado(ganado);
            return ResponseEntity.status(HttpStatus.CREATED).body(newGanado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        }
    }

    // ✅ Actualizar datos de un ganado
    @PutMapping("/{ganadoId}")
    public ResponseEntity<Ganado> updateGanado(@PathVariable Long ganadoId, @RequestBody Ganado newGanado) {
        Optional<Ganado> updatedGanado = ganadoService.updateGanado(ganadoId, newGanado);

        return updatedGanado.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build()); // ✅ Devuelve un 404 si no encuentra el ganado
    }

    // ✅ Eliminar un ganado
    @DeleteMapping("/{ganadoId}")
    public ResponseEntity<?> deleteGanado(@PathVariable Long ganadoId) {
        boolean deleted = ganadoService.deleteGanado(ganadoId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: No se encontró el ganado con ID " + ganadoId);
        }
    }

    // ✅ Obtener el ganado que está cerca del límite de 90 días
    @GetMapping("/cerca-limite")
    public ResponseEntity<List<Ganado>> getGanadoOrdenadoPorTiempo() {
        List<Ganado> ganadoList = ganadoService.getGanadoOrdenadoPorTiempo();
        return ResponseEntity.ok(ganadoList);
    }
}
