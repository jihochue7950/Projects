package com.example.demo;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//웹에 이미지가 들어나게 하는 부분
@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String dirName = "bookCover";
		Path bookCoverDir = Paths.get(dirName);
		String bookCoverPath = bookCoverDir.toFile().getAbsolutePath();
		
		String os = System.getProperty("os.name").toLowerCase();
		String file= os.contains("win") ? "file:/" : "file:";
//		윈도우 작동, 맥으로 테스트 해보고 지울 것
//		String file= "";
//	        if (os.contains("win")) {
//	        	file = "file:/";
//	        } else if (os.contains("mac")) {
//	        	file = "file:";
//	        } else {
//	        	System.err.println("unknown operating system");
//	        }
		registry.addResourceHandler("/" + dirName + "/**").addResourceLocations(file + bookCoverPath + "/");
	}

}