package com.example.celular.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.celular.Model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByCorreo(String correo);
}