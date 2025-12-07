package itmo.isproject

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.web.config.EnableSpringDataWebSupport

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
class IsProjectApplication {

    @Bean
    fun jwtBearer(): OpenAPI {
        return OpenAPI()
            .addSecurityItem(SecurityRequirement().addList("JWT Bearer"))
            .components(
                Components().addSecuritySchemes(
                    "JWT Bearer",
                    SecurityScheme()
                        .name("Authorization")
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                )
            )
    }
}

fun main(args: Array<String>) {
    runApplication<IsProjectApplication>(*args)
}
