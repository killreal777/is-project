package itmo.isproject.trade.model

import itmo.isproject.shared.common.model.IntIdEntity
import itmo.isproject.shared.user.model.User
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "trade")
class Trade(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    var user: User? = null,

    @CreationTimestamp
    @Column(name = "time", nullable = false)
    var time: LocalDateTime? = null,

    @OneToMany(mappedBy = "trade", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var items: MutableList<TradeItem> = ArrayList()

) : IntIdEntity()
