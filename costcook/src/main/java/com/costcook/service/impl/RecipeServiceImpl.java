package com.costcook.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.costcook.domain.request.RecipeRequest;
import com.costcook.domain.response.RecipeResponse;
import com.costcook.entity.ImageFile;
import com.costcook.entity.Recipe;
import com.costcook.repository.RecipeRepository;
import com.costcook.service.ImageFileService;
import com.costcook.service.RecipeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {
	
	private final RecipeRepository recipeRepository;
	private final ImageFileService imageFileService;
	
	// 레시피 등록
	@Override
	public RecipeResponse insertRecipe(RecipeRequest recipeRequest, MultipartFile file) {
		// 이미지 저장
		ImageFile savedImage = imageFileService.saveImage(file);
		if (savedImage != null) recipeRequest.setImageFile(savedImage); 
//		// 상품 추가 (관리자) // 현재 로그인한 유저의 ID로 지정
//		User user = userRepository.findById(loginedUser.getId()) 
//				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
		
//		Recipe recipe = recipeRequest.toEntity(user);
		Recipe recipe = recipeRequest.toEntity();
		Recipe savedRecipe = recipeRepository.save(recipe);
		RecipeResponse result = RecipeResponse.toDTO(savedRecipe);
		
		return result;
	}
	

	// 레시피 전체 목록
	@Override
	public List<RecipeResponse> getAllRecipe() {
		List<Recipe> recipeList = recipeRepository.findAll();
		if (recipeList.size() > 0) {
			List<RecipeResponse> recipeResponseList = recipeList.stream().map(RecipeResponse::toDTO).toList();
			return recipeResponseList;
		} else {
			return new ArrayList<>();
		}
	}
	

	// 특정 레시피 조회
	@Override
	public RecipeResponse getRecipeById(Long id) {
		Recipe product = recipeRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("제품 정보가 없습니다."));
		RecipeResponse productResponse = RecipeResponse.toDTO(product);
		return productResponse;
	}


	// 레시피 수정
	@Override
//	public RecipeResponse updateRecipe(RecipeRequest recipeRequest, MultipartFile file, User loginedUser) {
	public RecipeResponse updateRecipe(RecipeRequest recipeRequest, MultipartFile file) {
		// 수정 전 정보
		Recipe recipe = recipeRepository.findById(recipeRequest.getId())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
//		// 관리자 여부 확인
//		if (!loginedUser.getRole().name().equals("ROLE_ADMIN")) {
//			throw new IllegalArgumentException("관리자만 수정할 수 있습니다.");
//		}
		
		// 이미지 수정
		ImageFile savedImage = imageFileService.saveImage(file);
		
		// 수정하지 않을 경우 그대로 놔두기(null)
		if (savedImage != null) recipe.setImage(savedImage);
		if (recipeRequest.getTitle() != null) recipe.setTitle(recipeRequest.getTitle());
		if (recipeRequest.getDescription() != null) recipe.setDescription(recipeRequest.getDescription());
		if (recipeRequest.getServings() != recipe.getServings()) recipe.setServings(recipeRequest.getServings());
		if (recipeRequest.getPrice() != recipe.getPrice()) recipe.setPrice(recipeRequest.getPrice());
		if (recipeRequest.getViewCount() != recipe.getViewCount()) recipe.setViewCount(recipeRequest.getViewCount());
		if (recipeRequest.getBookmarkCount() != recipe.getBookmarkCount()) recipe.setBookmarkCount(recipeRequest.getBookmarkCount());
		if (recipeRequest.getCommentCount() != recipe.getCommentCount()) recipe.setCommentCount(recipeRequest.getCommentCount());
		if (recipeRequest.getAvgRatings() != recipe.getAvgRatings()) recipe.setAvgRatings(recipeRequest.getAvgRatings());
		
		Recipe updatedRecipe = recipeRepository.save(recipe);
		RecipeResponse result = RecipeResponse.toDTO(updatedRecipe);
		
		return result;
	}

	

}
