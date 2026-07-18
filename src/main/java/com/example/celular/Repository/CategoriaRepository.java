package com.example.celular.Repository;

import com.example.celular.Model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    Optional<Categoria> findByNombre(String nombre);
    List<Categoria> findByNombreIn(List<String> nombres);
}