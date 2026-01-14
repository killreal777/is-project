package itmo.isproject.production.dto

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import itmo.isproject.shared.user.dto.UserDto

data class ProductionModuleDto(

    @Schema(example = "1")
    @JsonProperty(value = "id", required = true)
    val id: Int?,

    @JsonProperty(value = "blueprint", required = true)
    val blueprint: ProductionModuleBlueprintDto,

    @JsonProperty(value = "engineer", required = true)
    val engineer: UserDto
)
