package com.costcook.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.costcook.domain.dto.FileDTO;
import com.costcook.entity.ImageFile;
import com.costcook.repository.ImageFileRepository;
import com.costcook.service.ImageFileService;
import com.costcook.util.FileUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageFileServiceImpl implements ImageFileService {
	
	private final ImageFileRepository ir;
	private final FileUtils fileUtils;
	
	@Override
	public ImageFile saveImage(MultipartFile file) {
			
		if (file != null) {
			// 파일 저장 후, 저장된 imageFile 객체 가져오기
			ImageFile imageFile = fileUtils.fileUpload(file);
			
			if (imageFile != null) {
				ImageFile savedImageFile = ir.save(imageFile);
				return savedImageFile;
			}			
		}
		return null;
	}
	
	
	// 이미지 다운로드
	@Override
	public FileDTO getImageByImageId(Long id) {
		ImageFile image = ir.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("파일을 찾을 수 없음"));
		return FileDTO.toDTO(image);
	}
	
	
	
	
}