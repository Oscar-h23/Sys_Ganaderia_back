package pe.idat.SistemaGanaderiaHuaman.service;

import pe.idat.SistemaGanaderiaHuaman.repository.RoleRepository;
import pe.idat.SistemaGanaderiaHuaman.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    // Inyección del repositorio para acceder a la persistencia de datos de Role.
    @Autowired
    private RoleRepository roleRepository;

    /**
     * Retorna una lista con todos los roles.
     *
     * @return Lista de roles.
     */
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    /**
     * Busca un role por su ID.
     *
     * @param id Identificador del role.
     * @return Optional que contiene el role si existe, o vacío si no se encuentra.
     */
    public Optional<Role> findById(Integer id) {
        return roleRepository.findById(id);
    }

    /**
     * Guarda un nuevo role en la base de datos.
     *
     * @param role Objeto Role a guardar.
     * @return Role guardado.
     */
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    /**
     * Actualiza los datos de un role existente.
     *
     * @param id   Identificador del role a actualizar.
     * @param role Objeto Role con los datos actualizados.
     * @return Role actualizado.
     * @throws RuntimeException Si no se encuentra el role con el ID proporcionado.
     */
    public Role update(Integer id, Role role) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (optionalRole.isPresent()) {
            Role existingRole = optionalRole.get();
            existingRole.setNombre(role.getNombre());
            existingRole.setDescripcion(role.getDescripcion());
            return roleRepository.save(existingRole);
        }
        throw new RuntimeException("Role no encontrado con id " + id);
    }

    /**
     * Elimina un role de la base de datos dado su ID.
     *
     * @param id Identificador del role a eliminar.
     */
    public void deleteById(Integer id) {
        roleRepository.deleteById(id);
    }
}
