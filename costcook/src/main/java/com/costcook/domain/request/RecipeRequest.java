package com.costcook.domain.request;

import com.costcook.entity.ImageFile;
import com.costcook.entity.Recipe;

import lombok.Data;

@Data
public class RecipeRequest {

	private Long id, categoryId;
	private String title, description;
	private int servings, price;
	private double avgRatings;
	
//	private Long authorId;
	private ImageFile imageFile;
	
//	public Recipe toEntity(User author) {
	public Recipe toEntity() {
		return Recipe.builder()
				.title(title)
				.description(description)
				.servings(servings)
				.price(price)
				.avgRatings(avgRatings)
//				.author(author)
				.image(imageFile)
				.build();
		
	}
	
	
}
