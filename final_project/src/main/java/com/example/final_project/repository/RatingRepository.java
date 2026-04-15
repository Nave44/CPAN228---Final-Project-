package com.example.final_project.repository;

import com.example.final_project.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
    Rating findByRecipeAndRater(Recipe recipe, User rater);
}
