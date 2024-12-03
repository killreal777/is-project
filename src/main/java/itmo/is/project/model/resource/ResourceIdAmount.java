package itmo.is.project.model.resource;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ResourceIdAmount implements ResourceIdAmountHolder {

    @NotNull
    private Integer resourceId;

    @With
    @NotNull
    @Min(1)
    private Integer amount;

    @Override
    public ResourceIdAmount getResourceIdAmount() {
        return this;
    }
}
