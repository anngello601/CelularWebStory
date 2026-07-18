package com.example.celular.Repository;

import com.example.celular.Model.Producto;
import com.example.celular.Model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    // Búsqueda por nombre (contiene)
    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    // Búsqueda por lista de marcas (entidades)
    List<Producto> findByMarcaIn(List<Marca> marcas);

    // Búsqueda por nombres de marcas (opcional)
    List<Producto> findByMarca_NombreIn(List<String> nombresMarca);
}