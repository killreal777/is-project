package itmo.is.project.repository.trade;

import itmo.is.project.model.trade.TradeItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeItemRepository extends JpaRepository<TradeItem, TradeItem.CompositeKey> {
}
