package itmo.isproject.trade.dto

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import itmo.isproject.shared.user.dto.UserDto
import java.time.LocalDateTime

data class TradeDto(

    @Schema(example = "1")
    @JsonProperty(value = "id", required = true)
    val id: Int?,

    @JsonProperty(value = "user", required = true)
    val user: UserDto,

    @Schema(example = "2024-11-29T10:48:08.248564")
    @JsonProperty(value = "time", required = true)
    val time: LocalDateTime,

    @JsonProperty(value = "items", required = true)
    val items: List<TradeItemDto>
)
