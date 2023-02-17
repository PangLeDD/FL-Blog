package com.lei.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @Author : 是大大大橙子噢
 * @Date : 2023/1/23 16:57
 * @Version : 1.0.0
 */
@Configuration
public class SwaggerConfig {


    @Bean
    public Docket customDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(info())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lei.controller"))
                .build();
    }
    private ApiInfo info(){
        return new ApiInfoBuilder()
                .contact(new Contact("范雷的毕设","www.lei.com","641472024@qq.com"))
                .description("作者：范雷")
                .title("程序员之家博客系统的设计与实现")
                .version("1.0")
                .build();
    }
}
