package com.example.PSAssignment.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;


import java.util.List;

@Entity
@Indexed
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recipe {

    @Id
    private Long id;

    @FullTextField
    private String name;

    @FullTextField
    private String cuisine;

    private String difficulty;
    private Integer prepTimeMinutes;
    private Integer cookTimeMinutes;
    private Integer servings;
    private Integer caloriesPerServing;
    private Double rating;
    private Integer reviewCount;
    private String image;

    @ElementCollection
    private List<String> ingredients;

    @ElementCollection
    private List<String> instructions;

    @ElementCollection
    private List<String> tags;

    @ElementCollection
    private List<String> mealType;
}

