package com.example.celular.Repository;

import com.example.celular.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByCorreoAndPassword(
            String correo,
            String password
    );
}