package itmo.is.project.model.resource;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ResourceAmount implements ResourceAmountHolder {

    @NotNull
    private Resource resource;

    @NotNull
    @Min(1)
    private Integer amount;

    public ResourceAmount(Integer resourceId, String resourceName, Integer amount) {
        this.resource = new Resource(resourceId, resourceName);
        this.amount = amount;
    }

    @Override
    public ResourceAmount getResourceAmount() {
        return this;
    }
}