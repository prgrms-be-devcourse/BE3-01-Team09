package org.programmer.cafe.global.config;

import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenApiCustomizer globalResponseCustomizer() {
        return openApi -> openApi.getPaths()
            .forEach((path, pathItem) -> pathItem.readOperations().forEach(operation -> {
                // ApiResponse 전역적으로 추가하려면 여기에 추가.
                operation.getResponses().addApiResponse("400",
                    new ApiResponse().description("잘못된 요청").content(new Content()
                        .addMediaType("application/json", new MediaType()
                            .schema(new Schema<>()
                                .type("object")
                                .addProperty("status",
                                    new Schema<>().type("integer").example(400))
                                .addProperty("message",
                                    new Schema<>().type("string").example("Validation failed"))
                            )
                        )
                    ));
                operation.getResponses().addApiResponse("500",
                    new ApiResponse().description("서버 오류").content(new Content()
                        .addMediaType("application/json", new MediaType()
                            .schema(new Schema<>()
                                .type("object")
                                .addProperty("status",
                                    new Schema<>().type("integer").example(500))
                                .addProperty("message", new Schema<>().type("string")
                                    .example("Internal server error"))
                            )
                        )
                    ));
            }));
    }
}
