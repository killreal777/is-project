package itmo.isproject.security.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive

@Validated
@ConfigurationProperties(prefix = "application.security.jwt")
data class SecurityConfigProperties(

    @NotBlank
    val secretKey: String,
    
    @Positive
    val expiration: Long
)
