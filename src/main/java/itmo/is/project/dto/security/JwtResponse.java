package itmo.is.project.dto.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import itmo.is.project.dto.user.UserDto;

public record JwtResponse(

        @Schema(example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJraWxscmVhbDc3NyIsImlhdCI6MTczMjg2MTEwNywiZXhwIjoxNzMyOTQ3NTA3fQ.qzKFP5gSrASGLjACOhydbhV7famSFSllK1xUjk8iXRg")
        @JsonProperty(value = "accessToken", required = true)
        String accessToken,

        @JsonProperty(value = "user", required = true)
        UserDto user
) {
}
