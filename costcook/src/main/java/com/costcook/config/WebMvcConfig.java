package com.costcook.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

	// application.yml의 location 정보 가져오기
	@Value("${spring.upload.location}")
	private String uploadPath;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry
			.addMapping("/**")
			.allowedOrigins("http://localhost:3000", "http://172.30.1.40:3000")
			.allowedMethods("OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE");
	}
	
	// 웹 페이지에서 이미지 보여주기
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
			.addResourceHandler("/img/**")
			.addResourceLocations("file:" + uploadPath + "\\");
	}
	
	
	
}