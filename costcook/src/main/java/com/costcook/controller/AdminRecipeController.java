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


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/recipes")
public class AdminRecipeController {
	
	private final RecipeService recipeService;
	
	// application.yml의 location 정보 가져오기
	@Value("${spring.upload.location}")
	private String uploadPath;
	
	// 레시피 등록
	@PostMapping("")
	public ResponseEntity<RecipeResponse> addRecipe(RecipeRequest recipeRequest, @RequestParam(name = "image", required = false) MultipartFile file) {
		log.info(file.getOriginalFilename());
		RecipeResponse savedRecipe = recipeService.insertRecipe(recipeRequest, file);
		
		return ResponseEntity.ok(savedRecipe);
	}
	
	
	// 관리자 전용 레시피 목록 조회 // /api/admin/recipes?page=1&limit=10
	@GetMapping(value = {"", "/"})
	public ResponseEntity<Map<String, Object>> AdminGetAllRecipe(
			@RequestParam(name = "page", defaultValue = "0") int page, 
			@RequestParam(name = "limit", defaultValue = "9") int limit
			) {
		
		// 오류처리 : ErrorResponse에서 처리하기
		// 오류처리. 400 Bad Request : page 또는 limit가 음수인 경우
		if (page < 0 || limit < 0) {
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("message", "페이지 번호와 페이지당 레시피 수는 0 이상이어야 합니다.");
			return ResponseEntity.badRequest().body(errorResponse);
		}
		
		// 레시피 목록 가져오기
		List<RecipeResponse> recipes = recipeService.getAdminRecipes(page, limit);
		// 총 레시피 개수
		long totalRecipes = recipeService.getTotalRecipes();
		// 총 페이지 수
		int totalPages = (int) Math.ceil((double) totalRecipes / limit);
		
		// 오류처리. 404 Not Found : 레시피가 없는 경우
		if (recipes.isEmpty()) {
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("message", "레시피를 찾을 수 없습니다.");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}
		
	
		// 응답할 데이터 구성
		Map<String, Object> response = new HashMap<>();
		response.put("page", page);
		response.put("size", limit);
		response.put("totalRecipes", totalRecipes); // 총 레시피 수
		response.put("totalPages", totalPages); // 총 페이지 수
		response.put("recipes", recipes); // 불러올 레시피 레이터
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	
	
	// 레시피 수정
	@PatchMapping("/{recipeId}")
	public ResponseEntity<RecipeResponse> modifyRecipe(RecipeRequest recipeRequest, @PathVariable("recipeId") Long id, @RequestParam(name ="image", required = false) MultipartFile file) {
		RecipeResponse updatedRecipe = recipeService.updateRecipe(recipeRequest, id, file);
		// 수정 완료,실패
		if (updatedRecipe != null) {
			return ResponseEntity.ok(updatedRecipe);
		}
		return null;
		
		
		
	}
	

	
	
	
	
	
	
	
	
}
