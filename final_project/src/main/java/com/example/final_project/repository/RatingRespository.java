package com.example.final_project.repository;

import com.example.final_project.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRespository extends JpaRepository<Rating, Integer> {
}
