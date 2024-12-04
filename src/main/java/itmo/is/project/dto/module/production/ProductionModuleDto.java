package itmo.is.project.dto.module.production;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import itmo.is.project.dto.user.UserDto;

public record ProductionModuleDto(

        @Schema(example = "1")
        @JsonProperty(value = "id", required = true)
        Integer id,

        @JsonProperty(value = "blueprint", required = true)
        ProductionModuleBlueprintDto productionModuleBlueprintDto,

        @JsonProperty(value = "engineer", required = true)
        UserDto engineer
) {
}
