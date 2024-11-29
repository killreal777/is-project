package itmo.is.project.repository.trade;

import itmo.is.project.dto.trade.TradeOfferDto;
import itmo.is.project.model.resource.Resource;
import itmo.is.project.model.trade.Trade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Integer> {

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
    Page<Object[]> findAllPurchaseOffersRaw(Pageable pageable);

    default Page<TradeOfferDto> findAllPurchaseOffers(Pageable pageable) {
        return findAllPurchaseOffersRaw(pageable)
                .map(this::parseTradeOffer);
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
    Optional<Object[]> findPurchaseOfferByResourceIdRaw(Integer resourceId);

    default TradeOfferDto findPurchaseOfferByResourceId(Integer resourceId) {
        return findPurchaseOfferByResourceIdRaw(resourceId)
                .map(rawSelect -> (Object[]) rawSelect[0])
                .map(this::parseTradeOffer)
                .orElseThrow();
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
    Page<Object[]> findAllSellOffersRaw(Pageable pageable);

    default Page<TradeOfferDto> findAllSellOffers(Pageable pageable) {
        return findAllSellOffersRaw(pageable)
                .map(this::parseTradeOffer);
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
    Optional<Object[]> findSellOfferByResourceIdRaw(Integer resourceId);

    default TradeOfferDto findSellOfferByResourceId(Integer resourceId) {
        return findSellOfferByResourceIdRaw(resourceId)
                .map(rawSelect -> (Object[]) rawSelect[0])
                .map(this::parseTradeOffer)
                .orElseThrow();
    }

    private TradeOfferDto parseTradeOffer(Object[] rawResult) {
        return new TradeOfferDto(
                (Resource) rawResult[0],
                ((Number) rawResult[1]).intValue(),
                ((Number) rawResult[2]).intValue()
        );
    }
}
