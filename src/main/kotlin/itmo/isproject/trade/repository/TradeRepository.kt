package itmo.isproject.trade.repository

import itmo.isproject.trade.dto.TradeOfferDto
import itmo.isproject.shared.resource.model.Resource
import itmo.isproject.trade.model.Trade
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TradeRepository : JpaRepository<Trade, Int> {

    fun findAllByUserId(userId: Int?, pageable: Pageable): Page<Trade>

    @Query("""
            SELECT
                tp.resource,
                GREATEST(COALESCE(SUM(sr.amount), 0) - tp.purchaseLimit, 0),
                tp.purchasePrice
            FROM TradePolicy tp
            JOIN StoredResource sr ON sr.resource = tp.resource
            WHERE tp.stationBuys = TRUE
            GROUP BY tp.resource, tp.purchaseLimit, tp.purchasePrice
            HAVING GREATEST(COALESCE(SUM(sr.amount), 0) - tp.purchaseLimit, 0) > 0
            """)
    fun findAllPurchaseOffersRaw(pageable: Pageable): Page<Array<Any>>

    fun findAllPurchaseOffers(pageable: Pageable): Page<TradeOfferDto> {
        return findAllPurchaseOffersRaw(pageable)
            .map { parseTradeOffer(it) }
    }

    @Query("""
            SELECT
                tp.resource,
                GREATEST(COALESCE(SUM(sr.amount), 0) - tp.purchaseLimit, 0),
                tp.purchasePrice
            FROM TradePolicy tp
            JOIN StoredResource sr ON sr.resource = tp.resource
            WHERE tp.stationBuys = TRUE AND tp.resourceId = :resourceId
            GROUP BY tp.resource, tp.purchaseLimit, tp.purchasePrice
            HAVING GREATEST(COALESCE(SUM(sr.amount), 0) - tp.purchaseLimit, 0) > 0
            """)
    fun findPurchaseOfferByResourceIdRaw(resourceId: Int?): Array<Array<Any>>

    fun findPurchaseOfferByResourceId(resourceId: Int?): TradeOfferDto? {
        val rawSelect = findPurchaseOfferByResourceIdRaw(resourceId)
        return if (rawSelect.isEmpty()) null else parseTradeOffer(rawSelect[0])
    }

    @Query("""           
            SELECT
                tp.resource,
                GREATEST(COALESCE(SUM(sr.amount), 0) - tp.sellLimit, 0),
                tp.sellPrice
            FROM TradePolicy tp
            JOIN StoredResource sr ON sr.resource = tp.resource
            WHERE tp.stationSells = TRUE
            GROUP BY tp.resource, tp.sellLimit, tp.sellPrice
            HAVING GREATEST(COALESCE(SUM(sr.amount), 0) - tp.sellLimit, 0) > 0
            """)
    fun findAllSellOffersRaw(pageable: Pageable): Page<Array<Any>>

    fun findAllSellOffers(pageable: Pageable): Page<TradeOfferDto> {
        return findAllSellOffersRaw(pageable)
            .map { parseTradeOffer(it) }
    }

    @Query("""           
            SELECT
                tp.resource,
                GREATEST(COALESCE(SUM(sr.amount), 0) - tp.sellLimit, 0),
                tp.sellPrice
            FROM TradePolicy tp
            JOIN StoredResource sr ON sr.resource = tp.resource
            WHERE tp.stationSells = TRUE AND tp.resourceId = :resourceId
            GROUP BY tp.resource, tp.sellLimit, tp.sellPrice
            HAVING GREATEST(COALESCE(SUM(sr.amount), 0) - tp.sellLimit, 0) > 0
            """)
    fun findSellOfferByResourceIdRaw(resourceId: Int?): Array<Array<Any>>

    fun findSellOfferByResourceId(resourceId: Int?): TradeOfferDto? {
        val rawSelect = findSellOfferByResourceIdRaw(resourceId)
        return if (rawSelect.isEmpty()) null else parseTradeOffer(rawSelect[0])
    }

    private fun parseTradeOffer(rawResult: Array<Any>): TradeOfferDto {
        return TradeOfferDto(
            rawResult[0] as Resource,
            (rawResult[1] as Number).toInt(),
            (rawResult[2] as Number).toInt()
        )
    }
}
