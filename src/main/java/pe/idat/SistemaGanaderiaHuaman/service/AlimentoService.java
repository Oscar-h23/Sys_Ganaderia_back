package pe.idat.SistemaGanaderiaHuaman.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pe.idat.SistemaGanaderiaHuaman.model.Alimento;
import pe.idat.SistemaGanaderiaHuaman.repository.AlimentoRepository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlimentoService {

    @Autowired
    private AlimentoRepository alimentoRepository;

    // Guardar un nuevo alimento con validaciones
    public Map<String, Object> guardarAlimento(Alimento alimento) {
        // Validaciones
        if (alimento == null) {
            throw new IllegalArgumentException("El alimento no puede ser nulo");
        }
        if (!esValido(alimento)) {
            throw new IllegalArgumentException("Los datos del alimento no son válidos");
        }
        calcularPrecioTotal(alimento); // Calcular el precio total
        Alimento nuevoAlimento = alimentoRepository.save(alimento);
        // Asegurarte de que venga con el proveedor cargado
        nuevoAlimento = alimentoRepository.findById(nuevoAlimento.getId()).orElseThrow();


        // Crear un mapa con los datos relevantes para la respuesta
        Map<String, Object> response = new HashMap<>();
        response.put("id", nuevoAlimento.getId());
        response.put("nombre", nuevoAlimento.getNombre());
        response.put("tipo", nuevoAlimento.getTipo());
        response.put("cantidad", nuevoAlimento.getCantidad());
        response.put("unidadMedida", nuevoAlimento.getUnidadMedida());
        response.put("precioPorUnidad", nuevoAlimento.getPrecioPorUnidad());
        response.put("precioTotal", nuevoAlimento.getPrecioTotal());
        response.put("fechaIngreso", nuevoAlimento.getFechaIngreso());
        response.put("observaciones", nuevoAlimento.getObservaciones());
        response.put("proveedorEmpresa", nuevoAlimento.getProveedor() != null ? nuevoAlimento.getProveedor().getEmpresa() : null);

        return response;
    }

    // Actualizar un alimento existente con validaciones
    public Alimento actualizarAlimento(int id, Alimento alimento) {
        // Validaciones
        if (alimento == null) {
            throw new IllegalArgumentException("El alimento no puede ser nulo");
        }
        if (!esValido(alimento)) {
            throw new IllegalArgumentException("Los datos del alimento no son válidos");
        }

        Optional<Alimento> alimentoExistente = alimentoRepository.findById(id);
        if (alimentoExistente.isPresent()) {
            Alimento alimentoActualizar = alimentoExistente.get();
            // Actualizar los campos relevantes
            alimentoActualizar.setNombre(alimento.getNombre());
            alimentoActualizar.setTipo(alimento.getTipo());
            alimentoActualizar.setCantidad(alimento.getCantidad());
            alimentoActualizar.setUnidadMedida(alimento.getUnidadMedida());
            alimentoActualizar.setPrecioPorUnidad(alimento.getPrecioPorUnidad());
            calcularPrecioTotal(alimentoActualizar); // Recalcular el precio total
            return alimentoRepository.save(alimentoActualizar);
        }
        throw new IllegalArgumentException("Alimento no encontrado con el ID proporcionado");
    }

    // Validar los datos del alimento
    private boolean esValido(Alimento alimento) {
        if (alimento.getNombre() == null || alimento.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre del alimento es obligatorio");
        }
        if (alimento.getTipo() == null || alimento.getTipo().isEmpty()) {
            throw new IllegalArgumentException("El tipo de alimento es obligatorio");
        }
        if (alimento.getCantidad() == null || alimento.getCantidad().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        }
        if (alimento.getPrecioPorUnidad() == null || alimento.getPrecioPorUnidad().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El precio por unidad debe ser mayor que cero");
        }
        return true;
    }

    // Eliminar un alimento por ID
    public boolean eliminarAlimento(int id) {
        Optional<Alimento> alimentoExistente = alimentoRepository.findById(id);
        if (alimentoExistente.isPresent()) {
            alimentoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Lógica para calcular el precio total basado en la cantidad y el precio por unidad
    private void calcularPrecioTotal(Alimento alimento) {
        BigDecimal cantidad = alimento.getCantidad();
        BigDecimal precioPorUnidad = alimento.getPrecioPorUnidad();

        if ("tonelada".equalsIgnoreCase(alimento.getUnidadMedida()) || "kg".equalsIgnoreCase(alimento.getUnidadMedida())) {
            alimento.setPrecioTotal(cantidad.multiply(precioPorUnidad));
        } else {
            alimento.setPrecioTotal(BigDecimal.ZERO);
        }
    }

    // Convertir lista de alimentos a una respuesta simplificada
    private List<Map<String, Object>> convertirARespuestaSimplificada(List<Alimento> alimentos) {
        return alimentos.stream().map(alimento -> {
            Map<String, Object> response = new HashMap<>();
            response.put("id", alimento.getId());
            response.put("nombre", alimento.getNombre());
            response.put("tipo", alimento.getTipo());
            response.put("cantidad", alimento.getCantidad());
            response.put("unidadMedida", alimento.getUnidadMedida());
            response.put("precioPorUnidad", alimento.getPrecioPorUnidad());
            response.put("precioTotal", alimento.getPrecioTotal());
            response.put("fechaIngreso", alimento.getFechaIngreso());
            response.put("observaciones", alimento.getObservaciones());
            response.put("proveedorEmpresa", alimento.getProveedor() != null ? alimento.getProveedor().getEmpresa() : null);
            return response;
        }).collect(Collectors.toList());
    }

    // Obtener todos los alimentos
    public List<Map<String, Object>> obtenerTodosLosAlimentos() {
        List<Alimento> alimentos = alimentoRepository.findAll();
        return convertirARespuestaSimplificada(alimentos);
    }

    // Buscar alimentos por proveedor (empresa)
    public List<Map<String, Object>> obtenerAlimentosPorProveedor(String empresa) {
        List<Alimento> alimentos = alimentoRepository.findByProveedorEmpresa(empresa);
        return convertirARespuestaSimplificada(alimentos);
    }

    // Buscar alimentos por tipo
    public List<Map<String, Object>> obtenerAlimentosPorTipo(String tipo) {
        List<Alimento> alimentos = alimentoRepository.findByTipo(tipo);
        return convertirARespuestaSimplificada(alimentos);
    }

    // Buscar alimento por ID
    public Optional<Map<String, Object>> obtenerAlimentoPorId(int id) {
        Optional<Alimento> alimentoExistente = alimentoRepository.findById(id);
        if (alimentoExistente.isPresent()) {
            Alimento alimento = alimentoExistente.get();
            return Optional.of(convertirARespuestaSimplificada(List.of(alimento)).get(0));
        }
        return Optional.empty();
    }
}
