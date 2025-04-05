package pe.idat.SistemaGanaderiaHuaman.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.idat.SistemaGanaderiaHuaman.model.Proveedor;
import pe.idat.SistemaGanaderiaHuaman.repository.ProveedorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    // Registrar o actualizar proveedor
    public Proveedor guardarProveedor(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    // Obtener todos los proveedores
    public List<Proveedor> listarProveedores() {
        return proveedorRepository.findAll();
    }

    // Obtener proveedor por ID
    public Optional<Proveedor> obtenerProveedorPorId(Long id) {
        return proveedorRepository.findById(id);
    }

    // Eliminar proveedor por ID
    public void eliminarProveedor(Long id) {
        proveedorRepository.deleteById(id);
    }

    // Buscar proveedor por correo
    public Optional<Proveedor> buscarPorCorreo(String correo) {
        return proveedorRepository.findByCorreo(correo);
    }

    // Buscar proveedor por nombre exacto de empresa
    public Optional<Proveedor> buscarPorEmpresa(String empresa) {
        return proveedorRepository.findByEmpresa(empresa);
    }

    // Buscar proveedores por empresa que contenga (ignora mayúsculas/minúsculas)
    public List<Proveedor> buscarPorEmpresaSimilar(String empresa) {
        return proveedorRepository.findByEmpresaContainingIgnoreCase(empresa);
    }
}
