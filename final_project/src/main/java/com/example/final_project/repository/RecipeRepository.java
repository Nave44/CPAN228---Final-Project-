package com.example.final_project.repository;

import com.example.final_project.model.Recipe;
import com.example.final_project.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    public List<Recipe> findByCategory(Optional<Category> category);
}
