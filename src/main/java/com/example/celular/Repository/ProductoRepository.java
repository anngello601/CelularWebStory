package com.example.celular.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.celular.Model.Producto;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    
    List<Producto> findByMarcaIn(List<String> marcas);

    // Búsqueda por nombre
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
}
