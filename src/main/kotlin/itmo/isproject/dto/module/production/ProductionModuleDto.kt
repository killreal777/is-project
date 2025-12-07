package itmo.isproject.dto.module.production

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import itmo.isproject.dto.user.UserDto

data class ProductionModuleDto(

    @Schema(example = "1")
    @JsonProperty(value = "id", required = true)
    val id: Int?,

    @JsonProperty(value = "blueprint", required = true)
    val blueprint: ProductionModuleBlueprintDto,

    @JsonProperty(value = "engineer", required = true)
    val engineer: UserDto
)
