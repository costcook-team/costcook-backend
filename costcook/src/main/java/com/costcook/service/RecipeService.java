package com.costcook.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.costcook.domain.request.RecipeRequest;
import com.costcook.domain.response.RecipeResponse;

public interface RecipeService {

	Page<RecipeResponse> getAllRecipes(Pageable pageable, Long id);

	RecipeResponse getRecipeById(Long id);

	RecipeResponse insertRecipe(RecipeRequest recipeRequest, MultipartFile file);

	RecipeResponse updateRecipe(RecipeRequest recipeRequest, MultipartFile file);

	RecipeResponse deleteRecipe(Long id);


}
