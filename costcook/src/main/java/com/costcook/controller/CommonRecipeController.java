package com.costcook.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.costcook.domain.request.RecipeRequest;
import com.costcook.domain.response.RecipeResponse;
import com.costcook.service.RecipeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// 모든 사용자가 조회 가능한 레시피 목록, 상세보기

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recipes")
public class CommonRecipeController {
	
	private final RecipeService recipeService;
	// application.yml의 location 정보 가져오기
	@Value("${spring.upload.location}")
	private String uploadPath;
	
	
	// 레시피 전체 목록 조회 
	@GetMapping(value = {"", "/"})
	public ResponseEntity<Map<String, Object>> getAllRecipe(
			@RequestParam(name = "page", defaultValue = "0") int page, 
			@RequestParam(name = "size", defaultValue = "9") int size, 
			@RequestParam(name = "sort", defaultValue = "createdAt") String sort,
			@RequestParam(name = "order", defaultValue = "desc") String order
			) {
		
		// 레시피 목록 가져오기
		List<RecipeResponse> recipes = recipeService.getRecipes(page, size, sort, order);
		
		// 총 레시피 개수 : 불러올 데이터가 없는 데 스크롤이 가능하면 계속해서 데이터를 불러옴 => 마지막 페이지를 설정해서 무한 로딩 방지
		long totalRecipes = recipeService.getTotalRecipes();
		// 불러올 데이터가 더 있는 지 확인
		boolean hasMore = (page + 1) * size < totalRecipes;
		
		// 응답할 데이터 구성
		Map<String, Object> response = new HashMap<>();
		response.put("page", page);
		response.put("size", size);
		response.put("totalRecipes", recipeService.getTotalRecipes()); 
		response.put("hasMore", hasMore); // 불러올 데이터가 더 있는지 확인
		response.put("recipes", recipes); // 불러올 레시피 데이터들
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	
	// 레시피 상세보기
	@GetMapping("/{id}")
	public ResponseEntity<RecipeResponse> getRecipe(@PathVariable("id") Long id) {
		RecipeResponse recipeResponse = recipeService.getRecipeById(id);
		return ResponseEntity.ok(recipeResponse);
	}
	
	
	
	
	
}
