package com.example.final_project.model;

import jakarta.persistence.*;

@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    int stars;
    @ManyToOne private User rater;
    @ManyToOne private Recipe recipe;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public User getRater() {
        return rater;
    }

    public void setRater(User rater) {
        this.rater = rater;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}

