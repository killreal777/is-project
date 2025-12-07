package itmo.isproject.dto.security

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

data class RegistrationRequest(

    @Schema(example = "killreal777")
    @JsonProperty(value = "username", required = true)
    val username: String,

    @Schema(example = "qwerty12345")
    @JsonProperty(value = "password", required = true)
    val password: String
)
