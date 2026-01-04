package itmo.isproject.service.trade

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.oshai.kotlinlogging.withLoggingContext
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

private val logger = KotlinLogging.logger {}

@Service
class TradePolicyService(
    private val tradePolicyRepository: TradePolicyRepository,
    private val tradePolicyMapper: TradePolicyMapper,
    private val resourceService: ResourceService
) {

    fun getAll(pageable: Pageable): Page<TradePolicyDto> {
        withLoggingContext("page" to pageable.pageNumber.toString()) {
            logger.debug { "Fetching all trade policies" }
        }
        return tradePolicyRepository.findAll(pageable).map { tradePolicyMapper.toDto(it) }
    }

    fun getAllStationBuys(pageable: Pageable): Page<TradePolicyDto> {
        withLoggingContext("page" to pageable.pageNumber.toString()) {
            logger.debug { "Fetching all station buys trade policies" }
        }
        return tradePolicyRepository.findAllByStationBuysTrue(pageable).map { tradePolicyMapper.toDto(it) }
    }

    fun getAllStationSells(pageable: Pageable): Page<TradePolicyDto> {
        withLoggingContext("page" to pageable.pageNumber.toString()) {
            logger.debug { "Fetching all station sells trade policies" }
        }
        return tradePolicyRepository.findAllByStationSellsTrue(pageable).map { tradePolicyMapper.toDto(it) }
    }

    fun getByResourceId(resourceId: Int): TradePolicyDto {
        withLoggingContext("resourceId" to resourceId.toString()) {
            logger.debug { "Fetching trade policy by resourceId" }
        }
        return tradePolicyRepository.findByIdOrNull(resourceId)?.let { tradePolicyMapper.toDto(it) }
            ?: throw EntityNotFoundException("Trade policy not found with resource id: $resourceId")
    }

    @Transactional
    fun updateByResourceId(resourceId: Int, request: UpdateTradePolicyRequest): TradePolicyDto {
        withLoggingContext(
            "resourceId" to resourceId.toString(),
            "stationBuys" to (request.stationBuys?.toString() ?: "null"),
            "stationSells" to (request.stationSells?.toString() ?: "null")
        ) {
            logger.info { "Updating trade policy" }
        }
        val resource = resourceService.getResourceById(resourceId)
        var tradePolicy = tradePolicyMapper.toEntity(request)
        tradePolicy.resource = resource
        tradePolicy = tradePolicyRepository.save(tradePolicy)
        withLoggingContext(
            "resourceId" to resourceId.toString(),
            "buyPrice" to (tradePolicy.purchasePrice?.toString() ?: "null"),
            "sellPrice" to (tradePolicy.sellPrice?.toString() ?: "null")
        ) {
            logger.info { "Trade policy updated" }
        }
        return tradePolicyMapper.toDto(tradePolicy)
    }
}
