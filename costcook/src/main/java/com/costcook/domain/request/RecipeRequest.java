package com.costcook.domain.request;

import com.costcook.entity.RecipeItem;

import lombok.Data;

@Data
public class RecipeRequest {

	private Long id, categoryId;
	private String title, description, thumbnailUrl;
	private int servings, price;
	private double avgRatings;
	
//	public Recipe toEntity(User author) {
	public RecipeItem toEntity() {
		return RecipeItem.builder()
				.title(title)
				.description(description)
				.servings(servings)
				.price(price)
				.avgRatings(avgRatings)
				.thumbnailUrl(thumbnailUrl)
				.build();
	}
	
	
}
