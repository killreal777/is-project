package itmo.is.project.dto.module.dock;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record DockModuleDto(

        @Schema(example = "1")
        @JsonProperty(value = "id", required = true)
        Integer id,

        @JsonProperty(value = "blueprint", required = true)
        DockModuleBlueprintDto blueprint,

        @JsonProperty(value = "dockingSpots", required = true)
        List<DockingSpotDto> dockingSpots
) {
}
