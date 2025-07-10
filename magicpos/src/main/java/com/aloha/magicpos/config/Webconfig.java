package com.aloha.magicpos.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Webconfig implements WebMvcConfigurer {
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {             // registry : 새로운 정적 자원(디렉토리) 등록하게 해주는 도구 
        registry.addResourceHandler("/upload/**")                   // addResourceHandler : "어떤 URL로 요청이 들어왔을때" 처리할 지 지정 
                                                                                    // (/upload/로 시작하는 모든 url요청 처리하겠다는 의미)
                .addResourceLocations("file:///C:/PMupload/");         // 위의 해당 요청이 들어왔을때 실제 어떤 디렉토리에서 파일을 꺼내줄지 지정
                // 즉, /upload/xxx.jpg 요청이 들어오면 → C:/PMupload/xxx.jpg 파일을 찾아서 브라우저에게 응답함 
    }
}
