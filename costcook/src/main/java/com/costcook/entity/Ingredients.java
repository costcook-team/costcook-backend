package com.costcook.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ingredients")
public class Ingredients {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false)
	private Long id;
	
	// 재료 이름
	@Column(nullable = false)
	private String name;
	
	// 카테고리 식별자 (디폴트 null, 외래키(카테고리 테이블))
	@Column(nullable = true)
	private Long categoryId;
	
	// 단위당 가격 (물 등 미입력 시 0원)
	@Column(nullable = false)
	@Builder.Default()
	private int price = 0;
	
	// 양
	@Column(nullable = false)
	@Builder.Default()
	private int quantity = 0;
	
	// 단위 (g, ml...)
	@Column(nullable = false)
	@Builder.Default()
	private String unit = "g";
	
	
	
	
	
}
