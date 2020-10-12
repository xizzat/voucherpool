package com.boost.voucherpool

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket


@Configuration
internal class AppConfig {
    @Bean
    fun swaggerConfigure(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(
                        RequestHandlerSelectors.basePackage("com.boost.voucherpool")
                )
                .build()
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
                .title("Voucher Pool Microservice")
                .description("This app will generate vouchers for the recipient")
                .build()
    }
}