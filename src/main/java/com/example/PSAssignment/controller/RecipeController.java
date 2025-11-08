package com.example.PSAssignment.controller;

import com.example.PSAssignment.model.Recipe;
import com.example.PSAssignment.service.RecipeService;
import jakarta.validation.*;
import jakarta.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/all")
    public List<Recipe> findAll(){
        return recipeService.findAllRecipes();
    }

    @GetMapping("/{id}")
    public Recipe findById(@PathVariable  @Min(value = 0, message = "Recipe ID must be positive") Long id) {
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

