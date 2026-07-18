package com.example.celular.Repository;

import com.example.celular.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByCorreo(String correo);
    List<User> findByRol(String rol);
    boolean existsByCorreo(String correo);
}