package itmo.isproject.repository.trade

import itmo.isproject.model.trade.TradePolicy
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TradePolicyRepository : JpaRepository<TradePolicy, Int> {

    fun findAllByStationBuysTrue(pageable: Pageable): Page<TradePolicy>

    fun findAllByStationSellsTrue(pageable: Pageable): Page<TradePolicy>
}
