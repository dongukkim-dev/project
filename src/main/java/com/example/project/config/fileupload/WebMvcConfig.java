package com.example.project.config.fileupload;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
public class WebMvcConfig {

    /**
     * 스프링 부트는 기본적으로 정적 파일을 불러올 때 현재 패키지의 static 폴더를 참조하기 때문에
     * 프로젝트 외부 폴더를 참조할 수 있도록 설정을 해줘야 한다.
     */
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**").addResourceLocations("file:///C:/delivery/upload/");
    }
}
