package itmo.is.project.repository.trade;

import itmo.is.project.model.trade.TradePolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradePolicyRepository extends JpaRepository<TradePolicy, Integer> {
    Page<TradePolicy> findAllByStationBuysTrue(Pageable pageable);

    Page<TradePolicy> findAllByStationSellsTrue(Pageable pageable);
}
