package com.costcook.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.costcook.domain.request.RecipeRequest;
import com.costcook.domain.response.RecipeResponse;

public interface RecipeService {

	List<RecipeResponse> getAllRecipe();

	RecipeResponse getRecipeById(Long id);

	RecipeResponse insertRecipe(RecipeRequest recipeRequest, MultipartFile file);

}
