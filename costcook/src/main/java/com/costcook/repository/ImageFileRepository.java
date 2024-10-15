package com.costcook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.costcook.entity.ImageFile;

@Repository
public interface ImageFileRepository extends JpaRepository<ImageFile, Long> {
	
	

}