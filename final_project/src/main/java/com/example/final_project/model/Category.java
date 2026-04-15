package com.example.final_project.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
    String description;
    @OneToMany(mappedBy = "category")
    List<Recipe> recipes;

//    @Entity
//    public class Post {
//        @Id private Long id;
//        @OneToMany(mappedBy = "post") List<Comment> comments;
//    }
//
//    @Entity
//    public class Comment {
//        @Id private Long id;
//        @ManyToOne private Post post;
//    }


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}
