package itmo.isproject.security.dto

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import itmo.isproject.shared.user.dto.UserDto

data class JwtResponse(

    @Schema(example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJraWxscmVhbDc3NyIsImlhdCI6MTczMjg2MTEwNywiZXhwIjoxNzMyOTQ3NTA3fQ.qzKFP5gSrASGLjACOhydbhV7famSFSllK1xUjk8iXRg")
    @JsonProperty(value = "accessToken", required = true)
    val accessToken: String,

    @JsonProperty(value = "user", required = true)
    val user: UserDto
)
