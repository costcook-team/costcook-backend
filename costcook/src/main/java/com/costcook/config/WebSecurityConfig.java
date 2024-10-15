package com.costcook.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;


import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

//	private final UserRepository userRepository;
//	private final UserDetailsService userDetailsService;
//	private final JwtProperties jwtProperties;
//	
//	
//	// JWT Provider
//	private JwtProvider jwtProvider() {
//		return new JwtProvider(userDetailsService, jwtProperties);
//	}
//	
//	// TokenUtils : 토큰 생성,관리 클래스
//	private TokenUtils tokenUtils() {
//		return new TokenUtils(jwtProvider());
//	}
//	
//	// JwtAuthenticationService : 로그인 인증 관련 서비스
//	private JwtAuthenticationService jwtAuthenticationService() {
//		return new JwtAuthenticationService(tokenUtils(), userRepository);
//	}
//	
//	
//	// AuthenticationManager : 인증 관리자 설정
//	@Bean
//	AuthenticationManager authenticationManager() {
//		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//		authProvider.setUserDetailsService(userDetailsService);
//		authProvider.setPasswordEncoder(bCryptPasswordEncoder());
//		
//		return new ProviderManager(authProvider);
//	}
//	
//	// 암호화 빈
//	@Bean
//	BCryptPasswordEncoder bCryptPasswordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
	
	
	// HTTP 요청에 따른 보안 구성
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		// 경로 권할 설정
		http.authorizeHttpRequests(auth ->
			// 누구나
			auth.requestMatchers(
					new AntPathRequestMatcher("/api/**"),
					new AntPathRequestMatcher("/api/recipe/**"),
					new AntPathRequestMatcher("/img/**")
//					new AntPathRequestMatcher("/api/oauth/**"),				
//					new AntPathRequestMatcher("/api/userjoin"),
//					new AntPathRequestMatcher("/api/duplicate"), // 회원가입 이메일 중복여부
//					new AntPathRequestMatcher("/api/refresh"), // 토큰 재발급
			).permitAll()
			// 인증 필요
//			.requestMatchers(
//					new AntPathRequestMatcher("/api/recipe", "POST"),
//					new AntPathRequestMatcher("/api/recipe/**", "DELETE"),
//					new AntPathRequestMatcher("/api/recipe/**", "PATCH")
//					).hasRole("ADMIN")
//			.anyRequest().authenticated()
		);
		
		// 무상태성 세션 관리
		http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//		// 특정 경로(Login)필터
//		http.addFilterBefore(new LoginCustomAuthenticationFilter(authenticationManager(), jwtAuthenticationService()), UsernamePasswordAuthenticationFilter.class);
//		// 토큰으로 검증할 수 있는 필터 추가
//		http.addFilterBefore(new JwtAuthenticationFilter(jwtProvider()), UsernamePasswordAuthenticationFilter.class);
		// HTTP 기본 설정
		http.httpBasic(HttpBasicConfigurer::disable);
		// CSRF 비활성화
		http.csrf(AbstractHttpConfigurer::disable);
		
		// CORS 설정
		http.cors(corsConfig -> corsConfig.configurationSource(CorsConfigurationSource()));
		
		return http.getOrBuild();
	}
	
	// CORS 설정 메소드
	@Bean
	CorsConfigurationSource CorsConfigurationSource() {
		return request -> {
			CorsConfiguration config = new CorsConfiguration();
			config.setAllowedHeaders(Collections.singletonList("*"));
			config.setAllowedMethods(Collections.singletonList("*"));
			config.setAllowedOriginPatterns(Collections.singletonList("http://localhost:3000"));
			config.setAllowCredentials(true);
			
			return config;
		};
	}
	
	
	
}