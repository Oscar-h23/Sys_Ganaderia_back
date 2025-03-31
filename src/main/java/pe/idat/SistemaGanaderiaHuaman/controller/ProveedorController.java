package pe.idat.SistemaGanaderiaHuaman.controller;

import pe.idat.SistemaGanaderiaHuaman.model.Proveedor;
import pe.idat.SistemaGanaderiaHuaman.service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/proveedores")
@CrossOrigin(originPatterns = "http://localhost:4200")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    /**
     * 📌 Listar proveedores con paginación.
     *
     * @param page Número de página (por defecto 0).
     * @param size Tamaño de la página (por defecto 5).
     * @return Página de proveedores.
     */
    @GetMapping
    public ResponseEntity<Page<Proveedor>> listarProveedores(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Proveedor> proveedores = proveedorService.findAll(pageable);

        return ResponseEntity.ok(proveedores);
    }

    /**
     * 📌 Buscar proveedor por ID.
     *
     * @param id ID del proveedor.
     * @return Proveedor encontrado o `404 Not Found`.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> buscarProveedorPorId(@PathVariable Long id) {
        Proveedor proveedor = proveedorService.findById(id);
        return proveedor != null ? ResponseEntity.ok(proveedor) : ResponseEntity.notFound().build();
    }

    /**
     * 📌 Buscar proveedores por empresa con paginación.
     *
     * @param empresa Nombre de la empresa a buscar.
     * @param page Número de página.
     * @param size Tamaño de la página.
     * @return Página de proveedores encontrados.
     */
    @GetMapping("/buscar")
    public ResponseEntity<Page<Proveedor>> buscarPorEmpresa(
            @RequestParam String empresa,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Proveedor> proveedores = proveedorService.findByEmpresa(empresa, pageable);

        return proveedores.hasContent() ? ResponseEntity.ok(proveedores) : ResponseEntity.notFound().build();
    }

    /**
     * 📌 Registrar un nuevo proveedor.
     *
     * @param proveedor Datos del proveedor a registrar.
     * @return Proveedor registrado.
     */
    @PostMapping("/register")
    public ResponseEntity<Proveedor> registrarProveedor(@RequestBody Proveedor proveedor) {
        Proveedor nuevoProveedor = proveedorService.save(proveedor);
        return ResponseEntity.ok(nuevoProveedor);
    }

    /**
     * 📌 Actualizar un proveedor existente.
     *
     * @param id ID del proveedor a actualizar.
     * @param proveedorActualizado Datos actualizados del proveedor.
     * @return Proveedor actualizado o `404 Not Found`.
     */
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

    /**
     * 📌 Eliminar un proveedor por ID.
     *
     * @param id ID del proveedor a eliminar.
     * @return `204 No Content` si se eliminó correctamente o `404 Not Found` si no existe.
     */
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
