package com.example.celular.Repository;

import com.example.celular.Model.Almacenamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AlmacenamientoRepository extends JpaRepository<Almacenamiento, Integer> {
    Optional<Almacenamiento> findByCapacidad(String capacidad);
}