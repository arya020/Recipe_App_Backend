package com.example.PSAssignment.controller;

import com.example.PSAssignment.exception.GlobalExceptionHandler;
import com.example.PSAssignment.model.Recipe;
import com.example.PSAssignment.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecipeController.class)
@Import(GlobalExceptionHandler.class)
@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RecipeService recipeService;

    @Test
    void whenLoadData_thenOk() throws Exception {
        // no return value expected
        mockMvc.perform(get("/load"))
                .andExpect(status().isOk());
        Mockito.verify(recipeService, Mockito.times(1)).loadRecipes();
    }

    @Test
    void whenFindById_validId_thenOk() throws Exception {
        Recipe mockRecipe = new Recipe();
        BDDMockito.given(recipeService.findById(1L)).willReturn(mockRecipe);

        mockMvc.perform(get("/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(recipeService, Mockito.times(1)).findById(1L);
    }

    @Test
    void whenFindById_invalidId_thenBadRequest() throws Exception {
        mockMvc.perform(get("/-5")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenSearchRecipes_withQuery_thenOk() throws Exception {
        Recipe r1 = new Recipe();
        Recipe r2 = new Recipe();
        List<Recipe> results = List.of(r1, r2);
        BDDMockito.given(recipeService.search("chicken")).willReturn(results);

        mockMvc.perform(get("/search")
                        .param("q", "chicken")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(recipeService, Mockito.times(1)).search("chicken");
    }

    @Test
    void whenReindex_thenOk() throws Exception {
        mockMvc.perform(post("/reindex")
                        .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk());
        Mockito.verify(recipeService, Mockito.times(1)).rebuildIndex();
    }
}

