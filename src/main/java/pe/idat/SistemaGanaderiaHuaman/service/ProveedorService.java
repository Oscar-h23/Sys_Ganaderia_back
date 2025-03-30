package pe.idat.SistemaGanaderiaHuaman.service;



import pe.idat.SistemaGanaderiaHuaman.model.Proveedor;
import pe.idat.SistemaGanaderiaHuaman.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProveedorService {

    // Inyección del repositorio para acceder a los datos de Proveedor
    @Autowired
    private ProveedorRepository proveedorRepository;

    /**
     * Retorna una lista con todos los proveedores.
     *
     * @return Lista de Proveedor.
     */
    public List<Proveedor> findAll() {
        return proveedorRepository.findAll();
    }

    /**
     * Busca un proveedor por su ID.
     *
     * @param id Identificador del proveedor.
     * @return Proveedor si se encuentra, o null en caso contrario.
     */
    public Proveedor findById(Long id) {
        return proveedorRepository.findById(id).orElse(null);
    }

    /**
     * Busca un proveedor por el nombre de la empresa.
     *
     * @param empresa Nombre de la empresa.
     * @return Proveedor si se encuentra, o null en caso contrario.
     */
    public Proveedor findByEmpresa(String empresa) {
        return proveedorRepository.findByEmpresa(empresa).orElse(null);
    }

    /**
     * Guarda o actualiza un proveedor en la base de datos.
     *
     * @param proveedor Objeto Proveedor a guardar.
     * @return Proveedor guardado.
     */
    public Proveedor save(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    /**
     * Elimina un proveedor dado su ID.
     *
     * @param id Identificador del proveedor a eliminar.
     */
    public void deleteById(Long id) {
        proveedorRepository.deleteById(id);
    }
}
