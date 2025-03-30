package pe.idat.SistemaGanaderiaHuaman.controller;

import pe.idat.SistemaGanaderiaHuaman.model.Corral;
import pe.idat.SistemaGanaderiaHuaman.service.EstablosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/establos/{establoId}/corrales")
public class CorralesController {

    private final EstablosService establosService;

    public CorralesController(EstablosService establosService) {
        this.establosService = establosService;
    }


    // Obtener todos los corrales de un establo
    @GetMapping
    public ResponseEntity<List<Corral>> getCorrales(@PathVariable Long establoId) {
        List<Corral> corrales = establosService.getCorrales(establoId);
        return ResponseEntity.ok(corrales);
    }


    // Crear un nuevo corral para un establo
    @PostMapping
    public ResponseEntity<Corral> createCorral(@PathVariable Long establoId, @RequestBody Corral corral) {
        Corral created = establosService.createCorral(establoId, corral);
        if (created != null) {
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        }
        return ResponseEntity.badRequest().build();
    }

    // Eliminar un corral de un establo
    @DeleteMapping("/{corralId}")
    public ResponseEntity<Void> deleteCorral(@PathVariable Long establoId, @PathVariable Long corralId) {
        if (establosService.deleteCorral(corralId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}