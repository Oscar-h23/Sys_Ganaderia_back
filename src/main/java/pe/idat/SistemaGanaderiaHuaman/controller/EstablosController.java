package pe.idat.SistemaGanaderiaHuaman.controller;


import pe.idat.SistemaGanaderiaHuaman.model.Establo;
import pe.idat.SistemaGanaderiaHuaman.service.EstablosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/establos")
public class EstablosController {

    private final EstablosService establosService;

    public EstablosController(EstablosService establosService) {
        this.establosService = establosService;
    }

    @GetMapping
    public List<Establo> getAllEstablos() {
        return establosService.getEstablos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Establo> getEstablo(@PathVariable Long id) {
        return establosService.getEstablo(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Establo> createEstablo(@RequestBody Establo establo) {
        return new ResponseEntity<>(establosService.createEstablo(establo), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Establo> updateEstablo(@PathVariable Long id, @RequestBody Establo establo) {
        Establo updated = establosService.updateEstablo(id, establo);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstablo(@PathVariable Long id) {
        if (establosService.deleteEstablo(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}