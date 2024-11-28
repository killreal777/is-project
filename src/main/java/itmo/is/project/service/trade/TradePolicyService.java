package itmo.is.project.service.trade;

import itmo.is.project.dto.trade.policy.TradePolicyDto;
import itmo.is.project.dto.trade.policy.UpdateTradePolicyRequest;
import itmo.is.project.mapper.trade.TradePolicyMapper;
import itmo.is.project.model.resource.Resource;
import itmo.is.project.model.trade.TradePolicy;
import itmo.is.project.repository.ResourceRepository;
import itmo.is.project.repository.trade.TradePolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TradePolicyService {
    private final TradePolicyRepository tradePolicyRepository;
    private final TradePolicyMapper tradePolicyMapper;
    private final ResourceRepository resourceRepository;

    public Page<TradePolicyDto> getAll(Pageable pageable) {
        return tradePolicyRepository.findAll(pageable).map(tradePolicyMapper::toDto);
    }

    public Page<TradePolicyDto> getAllStationBuys(Pageable pageable) {
        return tradePolicyRepository.findAllByStationBuysTrue(pageable).map(tradePolicyMapper::toDto);
    }

    public Page<TradePolicyDto> getAllStationSells(Pageable pageable) {
        return tradePolicyRepository.findAllByStationSellsTrue(pageable).map(tradePolicyMapper::toDto);
    }

    public TradePolicyDto getByResourceId(Integer resourceId) {
        return tradePolicyRepository.findById(resourceId).map(tradePolicyMapper::toDto).orElse(null);
    }

    public TradePolicyDto updateByResourceId(Integer resourceId, UpdateTradePolicyRequest request) {
        Resource resource = resourceRepository.findById(resourceId).orElseThrow();
        TradePolicy tradePolicy = tradePolicyMapper.toEntity(request);
        tradePolicy.setResourceId(resourceId);
        tradePolicy.setResource(resource);
        return tradePolicyMapper.toDto(tradePolicyRepository.save(tradePolicy));
    }
}
