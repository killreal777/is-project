package itmo.is.project.model.module;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "build_cost")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BuildCost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToMany(mappedBy = "buildCost", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<BuildCostItem> items;
}
