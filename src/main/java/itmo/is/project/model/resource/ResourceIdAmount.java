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
public class ResourceIdAmount {

    @NotNull
    private Integer id;

    @With
    @NotNull
    @Min(1)
    private Integer amount;
}
