package itmo.isproject.model.user

import itmo.isproject.model.IntIdEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "spaceship")
class Spaceship(

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pilot_id", referencedColumnName = "id", nullable = false, unique = true)
    var pilot: User? = null,

    @NotNull
    @Column(name = "size", length = 1, nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    var size: Size? = null

) : IntIdEntity() {

    enum class Size {
        S, M, L
    }
}
