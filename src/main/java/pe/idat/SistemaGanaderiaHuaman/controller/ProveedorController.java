package pe.idat.SistemaGanaderiaHuaman.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.idat.SistemaGanaderiaHuaman.model.Proveedor;
import pe.idat.SistemaGanaderiaHuaman.service.ProveedorService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/proveedores")
@CrossOrigin(origins = "*") // Puedes restringirlo a tu dominio Angular si lo deseas
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    // Registrar nuevo proveedor
    @PostMapping
    public ResponseEntity<Proveedor> registrarProveedor(@RequestBody Proveedor proveedor) {
        Proveedor nuevo = proveedorService.guardarProveedor(proveedor);
        return ResponseEntity.ok(nuevo);
    }

    // Listar todos los proveedores
    @GetMapping
    public ResponseEntity<List<Proveedor>> listarProveedores() {
        return ResponseEntity.ok(proveedorService.listarProveedores());
    }

    // Buscar proveedor por ID
    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> obtenerPorId(@PathVariable Long id) {
        return proveedorService.obtenerProveedorPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Actualizar proveedor
    @PutMapping("/{id}")
    public ResponseEntity<Proveedor> actualizarProveedor(@PathVariable Long id, @RequestBody Proveedor proveedor) {
        Optional<Proveedor> existente = proveedorService.obtenerProveedorPorId(id);
        if (existente.isPresent()) {
            proveedor.setId(id);
            return ResponseEntity.ok(proveedorService.guardarProveedor(proveedor));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar proveedor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProveedor(@PathVariable Long id) {
        if (proveedorService.obtenerProveedorPorId(id).isPresent()) {
            proveedorService.eliminarProveedor(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Buscar proveedor por correo exacto
    @GetMapping("/buscar/correo")
    public ResponseEntity<Proveedor> buscarPorCorreo(@RequestParam String correo) {
        return proveedorService.buscarPorCorreo(correo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Buscar proveedor por empresa exacta
    @GetMapping("/buscar/empresa")
    public ResponseEntity<Proveedor> buscarPorEmpresa(@RequestParam String empresa) {
        return proveedorService.buscarPorEmpresa(empresa)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Buscar proveedores por coincidencia en nombre de empresa (ignorando mayúsculas)
    @GetMapping("/buscar/empresa-similar")
    public ResponseEntity<List<Proveedor>> buscarPorEmpresaSimilar(@RequestParam String empresa) {
        List<Proveedor> resultados = proveedorService.buscarPorEmpresaSimilar(empresa);
        return ResponseEntity.ok(resultados);
    }
}
