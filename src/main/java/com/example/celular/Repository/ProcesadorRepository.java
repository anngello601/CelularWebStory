package com.example.celular.Repository;

import com.example.celular.Model.Procesador;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProcesadorRepository extends JpaRepository<Procesador, Integer> {
    Optional<Procesador> findByModelo(String modelo);
}