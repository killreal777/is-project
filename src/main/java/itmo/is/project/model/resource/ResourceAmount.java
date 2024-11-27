package itmo.is.project.model.resource;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ResourceAmount {
//public class ResourceAmount implements ResourceAmountHolder {

    @NotNull
    private Resource resource;

    @NotNull
    @Min(1)
    private Integer amount;

    public ResourceAmount(Number resourceId, String resourceName, Number amount) {
        this.resource = new Resource(resourceId.intValue(), resourceName);
        this.amount = amount.intValue();
    }

//    @Override
//    public ResourceAmount getResourceAmount() {
//        return this;
//    }
}
