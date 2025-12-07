package itmo.isproject.service.trade

import itmo.isproject.dto.trade.policy.TradePolicyDto
import itmo.isproject.dto.trade.policy.UpdateTradePolicyRequest
import itmo.isproject.mapper.trade.TradePolicyMapper
import itmo.isproject.repository.trade.TradePolicyRepository
import itmo.isproject.service.resource.ResourceService
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TradePolicyService(
    private val tradePolicyRepository: TradePolicyRepository,
    private val tradePolicyMapper: TradePolicyMapper,
    private val resourceService: ResourceService
) {

    fun getAll(pageable: Pageable): Page<TradePolicyDto> {
        return tradePolicyRepository.findAll(pageable).map { tradePolicyMapper.toDto(it) }
    }

    fun getAllStationBuys(pageable: Pageable): Page<TradePolicyDto> {
        return tradePolicyRepository.findAllByStationBuysTrue(pageable).map { tradePolicyMapper.toDto(it) }
    }

    fun getAllStationSells(pageable: Pageable): Page<TradePolicyDto> {
        return tradePolicyRepository.findAllByStationSellsTrue(pageable).map { tradePolicyMapper.toDto(it) }
    }

    fun getByResourceId(resourceId: Int): TradePolicyDto {
        return tradePolicyRepository.findByIdOrNull(resourceId)?.let { tradePolicyMapper.toDto(it) }
            ?: throw EntityNotFoundException("Trade policy not found with resource id: $resourceId")
    }

    @Transactional
    fun updateByResourceId(resourceId: Int, request: UpdateTradePolicyRequest): TradePolicyDto {
        val resource = resourceService.getResourceById(resourceId)
        var tradePolicy = tradePolicyMapper.toEntity(request)
        tradePolicy.resource = resource
        tradePolicy = tradePolicyRepository.save(tradePolicy)
        return tradePolicyMapper.toDto(tradePolicy)
    }
}
