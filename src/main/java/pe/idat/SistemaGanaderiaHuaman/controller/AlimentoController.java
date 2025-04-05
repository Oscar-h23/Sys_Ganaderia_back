package pe.idat.SistemaGanaderiaHuaman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.idat.SistemaGanaderiaHuaman.model.Alimento;
import pe.idat.SistemaGanaderiaHuaman.service.AlimentoService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/alimentos")
public class AlimentoController {

    @Autowired
    private AlimentoService alimentoService;

    // Crear un nuevo alimento
    @PostMapping
    public ResponseEntity<Map<String, Object>> crearAlimento(@RequestBody Alimento alimento) {
        try {
            Map<String, Object> response = alimentoService.guardarAlimento(alimento);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // Si los datos no son válidos, devolver una respuesta con un error
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    // Obtener todos los alimentos
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> obtenerTodosLosAlimentos() {
        List<Map<String, Object>> alimentos = alimentoService.obtenerTodosLosAlimentos();
        return new ResponseEntity<>(alimentos, HttpStatus.OK);
    }

    // Obtener alimentos por proveedor (empresa)
    @GetMapping("/proveedor/{empresa}")
    public ResponseEntity<List<Map<String, Object>>> obtenerAlimentosPorProveedor(@PathVariable String empresa) {
        List<Map<String, Object>> alimentos = alimentoService.obtenerAlimentosPorProveedor(empresa);
        return new ResponseEntity<>(alimentos, HttpStatus.OK);
    }

    // Obtener alimentos por tipo
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Map<String, Object>>> obtenerAlimentosPorTipo(@PathVariable String tipo) {
        List<Map<String, Object>> alimentos = alimentoService.obtenerAlimentosPorTipo(tipo);
        return new ResponseEntity<>(alimentos, HttpStatus.OK);
    }

    // Obtener un alimento por ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtenerAlimentoPorId(@PathVariable int id) {
        Optional<Map<String, Object>> alimento = alimentoService.obtenerAlimentoPorId(id);
        if (alimento.isPresent()) {
            return new ResponseEntity<>(alimento.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Map.of("error", "Alimento no encontrado"), HttpStatus.NOT_FOUND);
        }
    }

    // Actualizar un alimento por ID
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizarAlimento(@PathVariable int id, @RequestBody Alimento alimento) {
        try {
            Alimento alimentoActualizado = alimentoService.actualizarAlimento(id, alimento);
            if (alimentoActualizado != null) {
                return new ResponseEntity<>(Map.of(
                        "id", alimentoActualizado.getId(),
                        "nombre", alimentoActualizado.getNombre(),
                        "tipo", alimentoActualizado.getTipo(),
                        "cantidad", alimentoActualizado.getCantidad(),
                        "unidadMedida", alimentoActualizado.getUnidadMedida(),
                        "precioPorUnidad", alimentoActualizado.getPrecioPorUnidad(),
                        "precioTotal", alimentoActualizado.getPrecioTotal(),
                        "fechaIngreso", alimentoActualizado.getFechaIngreso(),
                        "observaciones", alimentoActualizado.getObservaciones(),
                        "proveedorEmpresa", alimentoActualizado.getProveedor() != null ? alimentoActualizado.getProveedor().getEmpresa() : null
                ), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Map.of("error", "Alimento no encontrado"), HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            // Si los datos no son válidos, devolver una respuesta con un error
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    // Eliminar un alimento por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminarAlimento(@PathVariable int id) {
        boolean eliminado = alimentoService.eliminarAlimento(id);
        if (eliminado) {
            return new ResponseEntity<>(Map.of("message", "Alimento eliminado correctamente"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Map.of("error", "Alimento no encontrado"), HttpStatus.NOT_FOUND);
        }
    }
}
