package com.costcook.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
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
import com.costcook.service.ImageFileService;
import com.costcook.service.RecipeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recipe/")
public class RecipeController {
	
	private final RecipeService recipeService;
	private final ImageFileService imageFileService;
	
	
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
	
	
	// 레시피 전체 목록 조회
	@GetMapping("")
	public ResponseEntity<List<RecipeResponse>> getAllRecipe(@RequestParam(name = "id", required = false) Long id) {
		List<RecipeResponse> result = new ArrayList<>();
		// 레시피 ID가 없으면 모든 레시피 조회, 있으면 리스트에 추가
		if (id == null) {
			result = recipeService.getAllRecipe();
		} else {
			RecipeResponse RecipeResponse = recipeService.getRecipeById(id);
			result.add(RecipeResponse);
		}
		return ResponseEntity.ok(result);
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
	public ResponseEntity<RecipeResponse> removeProduct(@PathVariable("id") Long id) {
//		// 로그인 유저(토큰을 통해 유저 정보 가져옴)
//		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		RecipeResponse deletedRecipe = recipeService.deleteProduct(id, user);
		RecipeResponse deletedRecipe = recipeService.deleteRecipe(id);

//		ProductResponse deletedProduct = productService.deleteProduct(id);
		
		return ResponseEntity.ok(deletedRecipe);
	}
		

	
	
	
}
