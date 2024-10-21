package com.costcook.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.costcook.domain.request.RecipeRequest;
import com.costcook.domain.response.RecipeResponse;
import com.costcook.entity.RecipeItem;
import com.costcook.repository.RecipeRepository;
import com.costcook.service.RecipeService;
import com.costcook.util.FileUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {
	
	private final RecipeRepository recipeRepository;
	private final FileUtils fileUtils;

	
	// 레시피 목록 조회
	@Override
	public List<RecipeResponse> getRecipes(int page, int size, String sort, String order) {
		Pageable pageable = PageRequest.of(page, size);
		Page<RecipeItem> recipePage;
		
		// 정렬
        if (sort.equals("viewCount")) {
        	if (order.equals("asc")) { // 오름차순
        		recipePage = recipeRepository.findAllByOrderByViewCountAsc(pageable);
        	} else { // 내림차순
        		recipePage = recipeRepository.findAllByOrderByViewCountDesc(pageable);
        	}
        } else if (sort.equals("avgRatings")) {
        	if (order.equals("asc")) { // 오름차순
        		recipePage = recipeRepository.findAllByOrderByAvgRatingsAsc(pageable);
        	} else { // 내림차순
        		recipePage = recipeRepository.findAllByOrderByAvgRatingsDesc(pageable);        		
        	}
        } else { // 생성일(디폴트)
        	if (order.equals("asc")) { // 오름차순
        		recipePage = recipeRepository.findAllByOrderByCreatedAtAsc(pageable);
        	} else {
        		recipePage = recipeRepository.findAllByOrderByCreatedAtDesc(pageable);        		
        	}
        }
		return recipePage.getContent().stream().map(RecipeResponse::toDTO).toList();
	}
	
	
	// 전체 레시피 수 조회 : 총 페이지를 미리 입력하여, 무한 로딩 방지
	@Override
	public long getTotalRecipes() {
		return recipeRepository.count();
	}

	
	// 레시피 상세 조회
	@Override
	public RecipeResponse getRecipeById(Long id) {
		RecipeItem product = recipeRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("제품 정보가 없습니다."));
		RecipeResponse productResponse = RecipeResponse.toDTO(product);
		return productResponse;
	}
	
	
	
	
	
	// 관리자용 레시피 목록 조회
	@Override
	public List<RecipeResponse> getAdminRecipes(int page, int limit) {
		Pageable pageable = PageRequest.of(page, limit);
		Page<RecipeItem> recipePage = recipeRepository.findAll(pageable);
		
		return recipePage.getContent().stream().map(RecipeResponse::toDTO).toList();
	}
	
	

	// 레시피 등록
	@Override
	public RecipeResponse insertRecipe(RecipeRequest recipeRequest, MultipartFile file) {
		try {
			
		// 이미지 저장
		if (file != null) {
			String thumbnailUrl = fileUtils.fileUpload(file);
			recipeRequest.setThumbnailUrl(thumbnailUrl);
		}
//		// 상품 추가 (관리자) // 현재 로그인한 유저의 ID로 지정
//		User user = userRepository.findById(loginedUser.getId()) 
//				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
		
//		Recipe recipe = recipeRequest.toEntity(user);
		RecipeItem recipe = recipeRequest.toEntity();
		RecipeItem savedRecipe = recipeRepository.save(recipe);
		RecipeResponse result = RecipeResponse.toDTO(savedRecipe);
		return result;
		
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("레시피 등록 오류");
		}
	}
	
	
	// 레시피 수정
	@Override
//	public RecipeResponse updateRecipe(RecipeRequest recipeRequest, MultipartFile file, User loginedUser) {
	public RecipeResponse updateRecipe(RecipeRequest recipeRequest, Long id, MultipartFile file) {
		// 수정 전 정보
		RecipeItem recipe = recipeRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 레시피입니다."));
//		// 관리자 여부 확인
//		if (!loginedUser.getRole().name().equals("ROLE_ADMIN")) {
//			throw new IllegalArgumentException("관리자만 수정할 수 있습니다.");
//		}
		
//		// 이미지 수정
		String savedImage = fileUtils.fileUpload(file);

		// 수정하지 않을 경우 그대로 놔두기(null)
		if (savedImage != null) recipe.setThumbnailUrl(savedImage);
		if (recipeRequest.getTitle() != null) recipe.setTitle(recipeRequest.getTitle());
		if (recipeRequest.getDescription() != null) recipe.setDescription(recipeRequest.getDescription());
		if (recipeRequest.getServings() != recipe.getServings()) recipe.setServings(recipeRequest.getServings());
		if (recipeRequest.getPrice() != recipe.getPrice()) recipe.setPrice(recipeRequest.getPrice());
		if (recipeRequest.getAvgRatings() != recipe.getAvgRatings()) recipe.setAvgRatings(recipeRequest.getAvgRatings());
		
		RecipeItem updatedRecipe = recipeRepository.save(recipe);
		RecipeResponse result = RecipeResponse.toDTO(updatedRecipe);
		
		return result;
	}








	// 레시피 삭제 미구현

	

}
