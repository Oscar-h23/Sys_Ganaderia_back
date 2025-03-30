package pe.idat.SistemaGanaderiaHuaman.controller;

import pe.idat.SistemaGanaderiaHuaman.model.Proveedor;
import pe.idat.SistemaGanaderiaHuaman.service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
@CrossOrigin(originPatterns = "http://localhost:4200")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    // Listar todos los proveedores
    @GetMapping
    public ResponseEntity<List<Proveedor>> listarProveedores() {
        List<Proveedor> proveedores = proveedorService.findAll();
        return ResponseEntity.ok(proveedores);
    }

    // Buscar proveedor por ID
    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> buscarProveedorPorId(@PathVariable Long id) {
        Proveedor proveedor = proveedorService.findById(id);
        return proveedor != null ? ResponseEntity.ok(proveedor) : ResponseEntity.notFound().build();
    }

    //buscar por empresa
    @GetMapping("/buscar")
    public ResponseEntity<List<Proveedor>> buscarPorEmpresa(@RequestParam String empresa) {
        List<Proveedor> proveedores = Collections.singletonList(proveedorService.findByEmpresa(empresa));
        return proveedores.isEmpty() ?  ResponseEntity.notFound().build() : ResponseEntity.ok(proveedores);
    }

    // Registrar proveedor (cambiado a /register para que coincida con el frontend)
    @PostMapping("/register")
    public ResponseEntity<Proveedor> registrarProveedor(@RequestBody Proveedor proveedor) {
        Proveedor nuevoProveedor = proveedorService.save(proveedor);
        return ResponseEntity.ok(nuevoProveedor);
    }

    // Actualizar proveedor
    @PutMapping("/{id}")
    public ResponseEntity<Proveedor> actualizarProveedor(@PathVariable Long id, @RequestBody Proveedor proveedorActualizado) {
        Proveedor proveedor = proveedorService.findById(id);
        if (proveedor == null) {
            return ResponseEntity.notFound().build();
        }
        proveedor.setRuc(proveedorActualizado.getRuc());
        proveedor.setEmpresa(proveedorActualizado.getEmpresa());
        proveedor.setRepresentante(proveedorActualizado.getRepresentante());
        proveedor.setTelefono(proveedorActualizado.getTelefono());
        proveedor.setCorreo(proveedorActualizado.getCorreo());
        proveedor.setEstado(proveedorActualizado.getEstado());

        Proveedor proveedorGuardado = proveedorService.save(proveedor);
        return ResponseEntity.ok(proveedorGuardado);
    }

    // Eliminar proveedor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProveedor(@PathVariable Long id) {
        Proveedor proveedor = proveedorService.findById(id);
        if (proveedor == null) {
            return ResponseEntity.notFound().build();
        }
        proveedorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
