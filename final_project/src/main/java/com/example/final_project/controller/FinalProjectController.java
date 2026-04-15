package com.example.final_project.controller;

import com.example.final_project.model.Recipe;
import com.example.final_project.model.User;
import com.example.final_project.repository.CategoryRepository;
import com.example.final_project.repository.RatingRespository;
import com.example.final_project.repository.RecipeRepository;
import com.example.final_project.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
// TODO Controller: post mapping to add ratings to recipes, post mapping for recipes, get mapping for all recipes, get for recipes by category

@Controller
public class FinalProjectController {

    User currentUser;

    private final UserRepository userRepository;
    private final RatingRespository ratingRespository;
    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;

    public FinalProjectController(UserRepository userRepository, RatingRespository ratingRespository, CategoryRepository categoryRepository, RecipeRepository recipeRepository) {
        this.userRepository = userRepository;
        this.ratingRespository = ratingRespository;
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
    }

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("recipes", recipeRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        return "home";
    }

    @GetMapping("/category/{id}")
    public String recipeByCategory(@PathVariable int id, Model model) {
        model.addAttribute("recipes", recipeRepository.findByCategory(categoryRepository.findById(id)));
        return "category";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register/{username}")
    public String register(@PathVariable String username) {
        currentUser = userRepository.saveWithUsername(username);
        return "home";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login/{username}")
    public String login(@PathVariable String username) {
        currentUser = userRepository.findByUsername(username);
        return "redirect:/home";
    }

    @GetMapping("/createRecipe")
    public String createRecipePage(Model model) {
        model.addAttribute("recipe", new Recipe());
        return "createRecipe";
    }

    @PostMapping("/createRecipe")
    public String createRecipe(@ModelAttribute Recipe recipe) {
        recipeRepository.save(recipe);
        return "redirect:/home";
    }
}
