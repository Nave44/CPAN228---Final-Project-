package com.example.final_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.final_project.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
