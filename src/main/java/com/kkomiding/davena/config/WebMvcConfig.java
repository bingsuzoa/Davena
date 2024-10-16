package com.kkomiding.davena.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.kkomiding.davena.common.hash.FileManager;
import com.kkomiding.davena.interceptor.PermissionInterceptor;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
	
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/**").addResourceLocations("file://" + FileManager.FILE_UPLOAD_PATH + "/");
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(permissionInterceptor())
				.addPathPatterns("/**")
				.excludePathPatterns("/static/**", "/user/logout", "/images/**");
	}
	
	@Bean
	public PermissionInterceptor permissionInterceptor() {
		return new PermissionInterceptor();
	}

}
