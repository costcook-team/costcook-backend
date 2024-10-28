package com.costcook.domain.response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.costcook.entity.RecipeItem;
import com.costcook.entity.Review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponse {
	
	
	

		
	private int score;
	private String comment;
	private boolean status;
	private String createdAt;
	private User user;
	private RecipeItem recipeItem;
	

	@Data
	@Builder
    @AllArgsConstructor
    @NoArgsConstructor
	public static class User{
		
		private Long id;
		private String nickname;
		private String profileUrl;
		
		
	}
	@Data
	@Builder
    @AllArgsConstructor
    @NoArgsConstructor
	public static class recipeItem{
		private Long id;
		private String thumbnailUrl;
	}
	

	// Review -> response 변환
	public static ReviewResponse toDTO(Review review) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    String formattedDate = review.getCreatedAt() != null ? review.getCreatedAt().format(formatter) : null;
		return ReviewResponse.builder()
				 .score(review.getScore())
                 .comment(review.getComment())
                 .status(review.isStatus())
                 .createdAt(formattedDate)
                 .user(User.builder()
                		  .id(review.getUser().getId())
                          .nickname(review.getUser().getNickname())
                          .profileUrl(review.getUser().getProfileUrl())
                          .build())
                 .recipeItem(RecipeItem.builder()
                         .id(review.getRecipeItem().getId())
                         .thumbnailUrl(review.getRecipeItem().getThumbnailUrl())
                         .build())
                 .build();						
	}
	
}