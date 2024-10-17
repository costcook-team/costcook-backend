package com.costcook.service;

import org.springframework.web.multipart.MultipartFile;

import com.costcook.domain.dto.FileDTO;
import com.costcook.entity.ImageFile;

public interface ImageFileService {
	
	public ImageFile saveImage(MultipartFile file);

	FileDTO getImageByImageId(Long id);
	
	
	
	
	
}