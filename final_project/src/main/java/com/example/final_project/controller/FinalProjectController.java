package com.example.final_project.controller;

import com.example.final_project.model.*;
import com.example.final_project.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Controller
public class FinalProjectController {

    User currentUser;

    private final UserRepository userRepository;
    private final RatingRepository ratingRepository;
    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;

    public FinalProjectController(UserRepository userRepository, RatingRepository ratingRepository, CategoryRepository categoryRepository, RecipeRepository recipeRepository) {
        this.userRepository = userRepository;
        this.ratingRepository = ratingRepository;
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/recipes")
    public String recipes(@RequestParam(required = false) Integer category,@RequestParam(required = false) Integer stars, Model model) {
        List<Recipe> recipes;
        List<Recipe> finalRecipes;

        if (category != null) {
            Category selectedCategory = categoryRepository.findById(category)
                    .orElse(null);
            if (selectedCategory != null) {
                recipes = recipeRepository.findByCategory(Optional.of(selectedCategory));
                model.addAttribute("selectedCategory", category);
            } else {
                recipes = recipeRepository.findAll();
            }
        } else {
            recipes = recipeRepository.findAll();
        }
        if (stars != null) {
            finalRecipes = recipes.stream()
                                    .filter(recipe -> recipe.checkRecipeByStar(stars))
                                    .collect(Collectors.toList());
        }   else {
            finalRecipes = recipes;
        }
        model.addAttribute("recipes", finalRecipes);
        model.addAttribute("categories", categoryRepository.findAll());
        return "recipes";
    }

    @PostMapping("/recipes/{id}/rate")
    public String rateRecipe(@PathVariable int id, @RequestParam int rating, RedirectAttributes redirectAttributes) {
        try {
            Recipe recipe = recipeRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Recipe not found"));

            Rating existingRating = ratingRepository.findByRecipeAndRater(recipe, currentUser);

            if (existingRating != null) {
                existingRating.setStars(rating);
                ratingRepository.save(existingRating);
                redirectAttributes.addFlashAttribute("message", "Rating updated to " + rating + " stars!");
            } else {
                Rating newRating = new Rating();
                newRating.setStars(rating);
                newRating.setRecipe(recipe);
                newRating.setRater(currentUser);
                ratingRepository.save(newRating);
                redirectAttributes.addFlashAttribute("message", "Thank you for rating " + rating + " stars!");
            }

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to save rating");
        }

        return "redirect:/recipes";
    }

    @GetMapping("/recipes/{id}")
    public String recipeDetail(@PathVariable int id, Model model) {
        var recipe = recipeRepository.findById(id).orElse(null);

        if (recipe != null && recipe.getInstructions() != null) {
            List<String> instructionList = Arrays.stream(recipe.getInstructions().split("\\. "))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());
            model.addAttribute("instructionList", instructionList);
        }

        model.addAttribute("recipe", recipe);
        model.addAttribute("id", id);
        return "recipe";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        currentUser = userRepository.save(user);
        return "redirect:/recipes";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user) {
        currentUser = userRepository.findByUsername(user.getUsername());
        return "redirect:/recipes";
    }

    @GetMapping("/createRecipe")
    public String createRecipePage(Model model) {
        model.addAttribute("recipe", new Recipe());
        model.addAttribute("categories", categoryRepository.findAll());
        return "createRecipe";
    }

    @PostMapping("/createRecipe")
    public String createRecipe(@ModelAttribute Recipe recipe) {
        recipe.setCreator(currentUser);
        Category category = categoryRepository.findById(recipe.getCategory().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid category"));
        recipe.setCategory(category);
        recipeRepository.save(recipe);
        return "redirect:/recipes";
    }
}
