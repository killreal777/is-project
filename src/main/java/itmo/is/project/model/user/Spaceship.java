package itmo.is.project.model.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "spaceship")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Spaceship {

    public enum Size {
        S, M, L
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pilot_id", referencedColumnName = "id", nullable = false, unique = true)
    private User pilot;

    @NotNull
    @Column(name = "size", length = 1, nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private Size size;
}

