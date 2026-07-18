package com.example.celular.Repository;

import com.example.celular.Model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface MarcaRepository extends JpaRepository<Marca, Integer> {
    Optional<Marca> findByNombre(String nombre);

    List<Marca> findByNombreIn(List<String> nombres);
}