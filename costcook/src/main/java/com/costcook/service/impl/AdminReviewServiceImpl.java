package com.costcook.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.costcook.domain.response.ReviewListResponse;
import com.costcook.domain.response.ReviewResponse;
import com.costcook.entity.Review;
import com.costcook.repository.RecipeRepository;
import com.costcook.repository.ReviewRepository;
import com.costcook.service.AdminReviewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminReviewServiceImpl implements AdminReviewService {

  private final RecipeRepository recipeRepository;
	private final ReviewRepository reviewRepository;

  @Override
    public ReviewListResponse getReviewList(int page, int size) {
        // 페이지네이션을 위한 Pageable 객체 생성 (페이지 번호는 0부터 시작)
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        // ReviewRepository에서 페이지네이션된 결과를 가져옴
        Page<Review> reviewPage = reviewRepository.findAll(pageable);

        // Page 객체에서 필요한 정보 추출
        List<Review> reviews = reviewPage.getContent();
        long totalElements = reviewPage.getTotalElements();
        int totalPages = reviewPage.getTotalPages();

        // ReviewListResponse 객체에 데이터를 담아 반환
        ReviewListResponse response = ReviewListResponse.builder()
                .reviews(reviews.stream().map(ReviewResponse::toDTO).collect(Collectors.toList()))
                .totalReviews(totalElements)
                .totalPages(totalPages)
                .page(page)
                .size(size)
                .build();

        return response;
    }

}
