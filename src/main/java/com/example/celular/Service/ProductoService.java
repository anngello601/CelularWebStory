package com.example.celular.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.celular.Model.Producto;
import com.example.celular.Repository.ProductoRepository;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository repo;

    public List<Producto> listar() {
        return repo.findAll();
    }

    public Producto buscarPorId(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public List<Producto> buscarPorMarcas(List<String> marcas) {
        return repo.findByMarcaIn(marcas);
    }

    // NUEVO
    public List<Producto> buscarPorNombre(String nombre) {
        return repo.findByNombreContainingIgnoreCase(nombre);
    }
}