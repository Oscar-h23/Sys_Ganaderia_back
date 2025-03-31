package pe.idat.SistemaGanaderiaHuaman.service;

import pe.idat.SistemaGanaderiaHuaman.model.Proveedor;
import pe.idat.SistemaGanaderiaHuaman.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProveedorService {

    // Inyección del repositorio para acceder a los datos de Proveedor
    @Autowired
    private ProveedorRepository proveedorRepository;

    /**
     * 📌 Retorna una lista paginada con todos los proveedores.
     *
     * @param pageable Objeto para manejar la paginación.
     * @return Página de Proveedores.
     */
    public Page<Proveedor> findAll(Pageable pageable) {
        return proveedorRepository.findAll(pageable);
    }

    /**
     * 📌 Busca un proveedor por su ID.
     *
     * @param id Identificador del proveedor.
     * @return Proveedor si se encuentra, o null en caso contrario.
     */
    public Proveedor findById(Long id) {
        return proveedorRepository.findById(id).orElse(null);
    }

    /**
     * 📌 Busca proveedores por el nombre de la empresa con paginación.
     *
     * @param empresa Nombre de la empresa.
     * @param pageable Objeto para manejar la paginación.
     * @return Página de Proveedores encontrados.
     */
    public Page<Proveedor> findByEmpresa(String empresa, Pageable pageable) {
        return proveedorRepository.findByEmpresaContainingIgnoreCase(empresa, pageable);
    }

    /**
     * 📌 Guarda o actualiza un proveedor en la base de datos.
     *
     * @param proveedor Objeto Proveedor a guardar.
     * @return Proveedor guardado.
     */
    public Proveedor save(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    /**
     * 📌 Elimina un proveedor dado su ID.
     *
     * @param id Identificador del proveedor a eliminar.
     */
    public void deleteById(Long id) {
        proveedorRepository.deleteById(id);
    }
}
