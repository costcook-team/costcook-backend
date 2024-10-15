package com.costcook.domain.request;

import com.costcook.entity.ImageFile;
import com.costcook.entity.Recipe;

import lombok.Data;

@Data
public class RecipeRequest {

	private Long id, categoryId;
	private String title, description;
	private Integer servings, price, viewCount, bookmarkCount, commentCount;
	private Double avgRatings;
	
//	private Long authorId;
	private ImageFile imageFile;
	
//	public Recipe toEntity(User author) {
	public Recipe toEntity() {
		return Recipe.builder()
				.title(title)
				.description(description)
				.servings(servings)
				.price(price)
				.viewCount(viewCount)
				.bookmarkCount(bookmarkCount)
				.commentCount(commentCount)
				.avgRatings(avgRatings)
//				.author(author)
				.image(imageFile)
				.build();
		
	}
	
	
}
