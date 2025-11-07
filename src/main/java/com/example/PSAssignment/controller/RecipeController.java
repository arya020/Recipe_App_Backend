package com.example.PSAssignment.controller;

import com.example.PSAssignment.model.Recipe;
import com.example.PSAssignment.service.RecipeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}")
    public Recipe findById(@PathVariable Long id) {
        return recipeService.findById(id);
    }

    @GetMapping("/search")
    public List<Recipe> searchRecipes(@RequestParam String q) {
        return recipeService.search(q);
    }

    @PostMapping("/reindex")
    public String reindex() {
        recipeService.rebuildIndex();
        return "Recipe index rebuilt successfully!";
    }
}

