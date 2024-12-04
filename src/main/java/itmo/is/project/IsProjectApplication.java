package itmo.is.project;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class IsProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(IsProjectApplication.class, args);
	}

	@Bean
	public OpenAPI jwtBearer() {
		return new OpenAPI()
				.addSecurityItem(new SecurityRequirement().addList("JWT Bearer"))
				.components(
						new Components().addSecuritySchemes(
								"JWT Bearer",
								new SecurityScheme()
										.name("Authorization")
										.type(SecurityScheme.Type.HTTP)
										.scheme("bearer")
										.bearerFormat("JWT")
						)
				);
	}
}
