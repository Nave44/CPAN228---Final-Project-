package com.example.final_project.model;

import jakarta.persistence.*;


@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
    String description;
    String ingredients;
    String instructions;
    @ManyToOne private User creator;
    @ManyToOne private Category category;

    public User getCreator() {
        return creator;
    }

    public Category getCategory() {
        return category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
