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

	

}
