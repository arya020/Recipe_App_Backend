package com.example.PSAssignment.service;

import com.example.PSAssignment.model.Recipe;
import com.example.PSAssignment.model.RecipeDTO;
import com.example.PSAssignment.repository.RecipeRepository;
import jakarta.persistence.EntityManager;
import org.hibernate.search.mapper.orm.Search;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.List;

@Service
public class RecipeService {

    private final EntityManager entityManager;
    private final RecipeRepository recipeRepository;
    private final RestTemplate restTemplate;

    String apiUrl = "https://dummyjson.com/recipes";

    public RecipeService(EntityManager entityManager, RecipeRepository recipeRepository,RestTemplate restTemplate) {
        this.entityManager = entityManager;
        this.recipeRepository = recipeRepository;
        this.restTemplate = restTemplate;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void loadDataAndReindex() {
        loadRecipes();
        rebuildIndex();
    }

    public Recipe save(Recipe Recipe) {

        return recipeRepository.save(Recipe);
    }

    public Recipe findById(Long id){

        return recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + id));
    }

    public List<Recipe> search(String text) {
        return Search.session(entityManager)
                .search(Recipe.class)
                .where(f -> f.bool(b -> {
                    b.should(f.simpleQueryString()
                            .fields("name", "cuisine")
                            .matching(text + "*"));
                }))
                .fetchAllHits();
    }

    public void rebuildIndex() {
        try {
            Search.session(entityManager)
                    .massIndexer(Recipe.class)
                    .startAndWait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadRecipes() {
        RecipeDTO response = restTemplate.getForObject(apiUrl, RecipeDTO.class);

        if (response != null && response.getRecipes() != null) {
            List<Recipe> recipes = response.getRecipes();

            List<Recipe> list = recipes.stream()
                    .map(dto -> Recipe.builder()
                            .id(dto.getId())
                            .name(dto.getName())
                            .cuisine(dto.getCuisine())
                            .difficulty(dto.getDifficulty())
                            .prepTimeMinutes(dto.getPrepTimeMinutes())
                            .cookTimeMinutes(dto.getCookTimeMinutes())
                            .servings(dto.getServings())
                            .caloriesPerServing(dto.getCaloriesPerServing())
                            .rating(dto.getRating())
                            .reviewCount(dto.getReviewCount())
                            .image(dto.getImage())
                            .ingredients(dto.getIngredients())
                            .instructions(dto.getInstructions())
                            .tags(dto.getTags())
                            .mealType(dto.getMealType())
                            .build())
                    .toList();

            list.sort(Comparator.comparing(Recipe::getId));

            recipeRepository.saveAll(list);
        }
    }
}

