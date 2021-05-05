package ru.stm.lot4.receiver.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    Docket swaggerConfig(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("ru.stm.lot4.statistic.controller"))
                .build()
                .useDefaultResponseMessages(false)
                .apiInfo(metaData())
                .tags(new Tag("statistic-application-controller",
                        "Web API для получения статистики приложения"));
    }

    private ApiInfo metaData() {
        return new ApiInfo(
                "Statistic Web-API",
                "Web-API для получения статистики приложения",
                "1.0",
                "Terms of service",
                new Contact("STM", "http://stm-labs.ru", "info@stm-labs.ru"),
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0",
                Collections.emptyList());
    }
}
