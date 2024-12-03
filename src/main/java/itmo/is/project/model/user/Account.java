package itmo.is.project.model.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    private Integer userId;

    @MapsId
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @NotNull
    @Min(0)
    @Column(name = "balance", nullable = false)
    private Integer balance = 0;

    public void deposit(Integer amount) {
        balance += amount;
    }

    public void withdraw(Integer amount) {
        balance -= amount;
    }
}

