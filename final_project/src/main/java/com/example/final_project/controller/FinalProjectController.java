package com.example.final_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.final_project.model.Recipe;
import com.example.final_project.model.User;
import com.example.final_project.repository.CategoryRepository;
import com.example.final_project.repository.RatingRespository;
import com.example.final_project.repository.RecipeRepository;
import com.example.final_project.repository.UserRepository;
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

    @GetMapping({"/", "/home"})
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

    @GetMapping("/recipes/{id}")
    public String recipeDetail(@PathVariable int id, Model model) {
        var recipe = recipeRepository.findById(id).orElse(null);
        model.addAttribute("recipe", recipe);
        model.addAttribute("id", id);
        return "recipe";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "redirect:/register.html";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username) {
        User existingUser = userRepository.findByUsername(username);
        if (existingUser != null) {
            currentUser = existingUser;
        } else {
            User user = new User();
            user.setUsername(username);
            currentUser = userRepository.save(user);
        }
        return "redirect:/home";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "redirect:/login.html";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username) {
        currentUser = userRepository.findByUsername(username);
        if (currentUser == null) {
            return "redirect:/register";
        }
        return "redirect:/home";
    }

    @GetMapping("/createRecipe")
    public String createRecipePage(Model model) {
        model.addAttribute("recipe", new Recipe());
        return "createRecipe";
    }

    @PostMapping("/createRecipe")
    public String createRecipe(@ModelAttribute Recipe recipe) {
        if (currentUser != null) {
            recipe.setCreator(currentUser);
        }
        recipeRepository.save(recipe);
        return "redirect:/home";
    }
}
