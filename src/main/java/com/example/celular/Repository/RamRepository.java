package com.example.celular.Repository;

import com.example.celular.Model.Ram;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RamRepository extends JpaRepository<Ram, Integer> {
    Optional<Ram> findByCapacidad(String capacidad);
}