package com.nexo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nexo.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByCorreoAndPassword(String correo, String password);

    User findByCorreo(String correo);

}