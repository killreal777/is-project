package itmo.is.project.model.trade;

import itmo.is.project.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "trade")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

//    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

//    @NotNull
    @CreationTimestamp
    @Column(name = "time", nullable = false)
    private LocalDateTime time;

    @OneToMany(mappedBy = "trade", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TradeItem> items;
}
