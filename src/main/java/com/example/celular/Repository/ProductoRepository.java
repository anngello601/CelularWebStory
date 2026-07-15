package com.example.celular.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.celular.Model.Producto;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    @Query(value = "SELECT id, nombre, marca, categoria, almacenamiento, ram, procesador, " +
                   "precio_normal, precio, precio_tarjeta, stock, imagen, rating, " +
                   "num_opiniones, ean_upc, tiempo_envio, garantia, condiciones, devoluciones " +
                   "FROM producto_definitivo ORDER BY id ASC", nativeQuery = true)
    List<Producto> findAll();
    
    List<Producto> findByMarcaIn(List<String> marcas);

    // Búsqueda por nombre
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
}
