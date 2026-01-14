package itmo.isproject.shared.user.dto

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

data class AccountDto(

    @JsonProperty(value = "user", required = true)
    val user: UserDto,

    @Schema(example = "1000000")
    @JsonProperty(value = "balance", required = true)
    val balance: Int?
)
