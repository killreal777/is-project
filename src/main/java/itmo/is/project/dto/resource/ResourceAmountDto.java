package itmo.is.project.dto.resource;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ResourceAmountDto(
        @JsonProperty(value = "resource")
        ResourceDto resource,

        @JsonProperty(value = "amount")
        Integer amount
) {
//    public ResourceAmountDto(Integer resourceId, String resourceName, Integer amount) {
//        this(new ResourceDto(resourceId, resourceName), amount);
//    }
}