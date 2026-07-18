package com.example.celular.Service;

import com.example.celular.Model.Producto;
import com.example.celular.Model.Marca;
import com.example.celular.Repository.ProductoRepository;
import com.example.celular.Repository.MarcaRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductoService {

    private final ProductoRepository productoRepo;
    private final MarcaRepository marcaRepo;

    // Inyectamos los repositorios necesarios
    public ProductoService(ProductoRepository productoRepo, MarcaRepository marcaRepo) {
        this.productoRepo = productoRepo;
        this.marcaRepo = marcaRepo;
    }

    public List<Producto> listar() {
        return productoRepo.findAll();
    }

    public Producto buscarPorId(Integer id) {
        return productoRepo.findById(id).orElse(null);
    }

    // 🔴 Cambio: ahora recibe nombres de marcas, busca las entidades y filtra
    public List<Producto> buscarPorMarcas(List<String> nombresMarcas) {
        if (nombresMarcas == null || nombresMarcas.isEmpty()) {
            return productoRepo.findAll();
        }
        // Buscar las marcas por nombre (puede usar findByNombreIn)
        List<Marca> marcas = marcaRepo.findByNombreIn(nombresMarcas);
        if (marcas.isEmpty()) {
            return List.of(); // No hay marcas, devolver lista vacía
        }
        return productoRepo.findByMarcaIn(marcas);
    }

    public List<Producto> buscarPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return List.of();
        }
        return productoRepo.findByNombreContainingIgnoreCase(nombre);
    }

    public Map<String, Object> reducirStock(Integer id, int cantidad) {
        Map<String, Object> resultado = new HashMap<>();
        Producto producto = productoRepo.findById(id).orElse(null);
        if (producto == null) {
            resultado.put("ok", false);
            resultado.put("mensaje", "Producto no encontrado");
            return resultado;
        }
        if (producto.getStock() < cantidad) {
            resultado.put("ok", false);
            resultado.put("mensaje", "Solo quedan " + producto.getStock() + " unidades disponibles");
            resultado.put("stockActual", producto.getStock());
            return resultado;
        }
        producto.setStock(producto.getStock() - cantidad);
        productoRepo.save(producto);
        resultado.put("ok", true);
        resultado.put("nuevoStock", producto.getStock());
        return resultado;
    }
}