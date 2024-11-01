package com.costcook.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.costcook.entity.Budget;
import com.costcook.entity.RecommendedRecipe;
import com.costcook.entity.User;

public interface RecommendedRecipeRepository extends JpaRepository<RecommendedRecipe, Long>{

}
