package com.costcook.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.costcook.entity.Recipe;
import com.costcook.service.ImageFileService;
import com.costcook.service.RecipeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recipes")
public class RecipeController {
	
	private final RecipeService recipeService;
	// application.yml의 location 정보 가져오기
	@Value("${spring.upload.location}")
	private String uploadPath;
	
	// 레시피 등록
	@PostMapping("")
	public ResponseEntity<RecipeResponse> addProduct(RecipeRequest recipeRequest, @RequestParam(name = "image", required = false) MultipartFile file) {

//		// 토큰을 통해 로그인 유저 정보를 가져옴
//		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//		RecipeResponse savedRecipe = recipeService.insertRecipe(recipeRequest, file, user);
		RecipeResponse savedRecipe = recipeService.insertRecipe(recipeRequest, file);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(savedRecipe);
	}
	
	
	// 레시피 전체 목록 조회 (페이지네이션)
	@GetMapping(value = {"", "/"})
	public ResponseEntity<Page<RecipeResponse>> getAllRecipe(
			@RequestParam(name = "id", required = false) Long id,
			@RequestParam(name = "page", defaultValue = "0") int page, 
			@RequestParam(name = "size", defaultValue = "9") int size, 
			@RequestParam(name = "sort", defaultValue = "createdAt") String sort,
			@RequestParam(name = "order", defaultValue = "desc") String order
			) {
		
		// 정렬 방향
		Sort.Direction direction = order.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
		// 정렬 기준
//		Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));
		Pageable pageable;
		// 정렬 기준에 따른 Pageable 생성
		if (sort.equals("avgRatings")) {
			pageable = PageRequest.of(page, size, Sort.by(direction, "avgRatings")); 
		} else if (sort.equals("viewCount")) {
			pageable = PageRequest.of(page, size, Sort.by(direction, "viewCount"));
		} else { // 기본 정렬
			pageable = PageRequest.of(page, size, Sort.by(direction, sort));
		}
			
		// 레시피 목록 가져오기
		Page<RecipeResponse> recipes = recipeService.getAllRecipes(pageable, id);
		
		return ResponseEntity.ok(recipes);
	}
	
	
	// 레시피 상세보기
	@GetMapping("/{id}")
	public ResponseEntity<RecipeResponse> getRecipe(@PathVariable("id") Long id) {
		RecipeResponse recipeResponse = recipeService.getRecipeById(id);
		return ResponseEntity.ok(recipeResponse);
	}
	
	
	// 레시피 수정
	@PatchMapping("")
	public ResponseEntity<RecipeResponse> modifyRecipe(RecipeRequest recipeRequest, @RequestParam(name = "image", required = false) MultipartFile file) {
//		// 토큰을 통해 로그인 유저 정보를 가져옴
//		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//		RecipeResponse updatedRecipe = recipeService.updateRecipe(RecipeRequest, file, user);
		RecipeResponse updatedRecipe = recipeService.updateRecipe(recipeRequest, file);
		
		return ResponseEntity.ok(updatedRecipe);
	}
	
	
	// 레시피 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<RecipeResponse> removeRecipe(@PathVariable("id") Long id) {
//		// 로그인 유저(토큰을 통해 유저 정보 가져옴)
//		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		RecipeResponse deletedRecipe = recipeService.deleteProduct(id, user);
		RecipeResponse deletedRecipe = recipeService.deleteRecipe(id);

//		ProductResponse deletedProduct = productService.deleteProduct(id);
		
		return ResponseEntity.ok(deletedRecipe);
	}
		

	
	
	
	
	
	
	
	
	
}
