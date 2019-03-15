package com.xyj.beans;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.AllowableListValues;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Value("${server.port}")
    private int port;
    @Value("${spring.application.name}")
    private String appName;

    @Bean
    public Docket createRestApi() throws UnknownHostException {
        String model = appName.split("-")[1];

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.xyj"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() throws UnknownHostException {
        String hostAddress = Inet4Address.getLocalHost().getHostAddress();

        String serviceUrl = "http://" + hostAddress + ":" + port + "/";

        return new ApiInfoBuilder()
                .title("后台管理模块")
                .description("后台管理模块")
                .contact(new Contact(appName, serviceUrl+"swagger-ui.html", "759261304@qq.com"))
                .termsOfServiceUrl(serviceUrl)
                .version("1.0")
                .build();
    }
}
