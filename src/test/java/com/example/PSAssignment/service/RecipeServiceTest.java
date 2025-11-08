package com.example.PSAssignment.service;

import com.example.PSAssignment.model.Recipe;
import com.example.PSAssignment.model.RecipeDTO;
import com.example.PSAssignment.repository.RecipeRepository;
import jakarta.persistence.EntityManager;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class RecipeServiceTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private SearchSession searchSession;

    @InjectMocks
    private RecipeService recipeService;

    @Test
    void whenFindById_validId_thenReturnRecipe() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));

        Recipe found = recipeService.findById(1L);

        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(1L);
        verify(recipeRepository, times(1)).findById(1L);
    }

    @Test
    void whenFindById_invalidId_thenThrowException() {
        when(recipeRepository.findById(-5L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> recipeService.findById(-5L));

        assertThat(exception.getMessage()).isEqualTo("Recipe not found with id: -5");
        verify(recipeRepository, times(1)).findById(-5L);
    }

    @Test
    void whenLoadRecipes_thenSaveAllCalled() {

        Recipe recipe = new Recipe();
        recipe.setName("Pasta");
        RecipeDTO dto = new RecipeDTO();
        dto.setRecipes(List.of(recipe));

        when(restTemplate.getForObject(anyString(), eq(RecipeDTO.class)))
                .thenReturn(dto);

        recipeService.loadRecipes();
        verify(recipeRepository, atLeastOnce()).saveAll(anyList());
    }


}

