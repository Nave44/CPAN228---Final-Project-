package com.example.final_project.repository;

import com.example.final_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByUsername(String username);
    public User saveWithUsername(String username);
}
