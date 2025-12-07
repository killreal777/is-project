package itmo.isproject.model.user

import itmo.isproject.model.IntIdEntity
import jakarta.persistence.*
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "account")
class Account(

    @MapsId
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    var user: User? = null,

    @NotNull
    @Min(0)
    @Column(name = "balance", nullable = false)
    var balance: Int = 0

) : IntIdEntity() {

    fun deposit(amount: Int?) {
        balance += amount ?: 0
    }

    fun withdraw(amount: Int?) {
        balance -= amount ?: 0
    }
}
