package itmo.isproject.shared.user.dto

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import itmo.isproject.shared.user.model.Role

data class UserDto(

    @Schema(example = "1")
    @JsonProperty(value = "id", required = true)
    val id: Int?,

    @Schema(example = "killreal777")
    @JsonProperty(value = "username", required = true)
    val username: String,

    @Schema(example = "ROLE_PILOT")
    @JsonProperty(value = "role", required = true)
    val role: Role
)
