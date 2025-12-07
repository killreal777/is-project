package itmo.isproject.service.trade

import itmo.isproject.dto.resource.ResourceIdAmountDto
import itmo.isproject.dto.trade.TradeDto
import itmo.isproject.dto.trade.TradeOfferDto
import itmo.isproject.dto.trade.TradeRequest
import itmo.isproject.mapper.trade.TradeMapper
import itmo.isproject.model.resource.ResourceIdAmount
import itmo.isproject.model.trade.Operation
import itmo.isproject.model.trade.Trade
import itmo.isproject.model.trade.TradeItem
import itmo.isproject.model.user.User
import itmo.isproject.repository.trade.TradeRepository
import itmo.isproject.service.module.StorageModuleService
import itmo.isproject.service.resource.ResourceService
import itmo.isproject.service.user.AccountService
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TradeService(
    private val tradeRepository: TradeRepository,
    private val tradeMapper: TradeMapper,
    private val accountService: AccountService,
    private val storageModuleService: StorageModuleService,
    private val resourceService: ResourceService
) {

    fun getAllTrades(pageable: Pageable): Page<TradeDto> {
        return tradeRepository.findAll(pageable).map { tradeMapper.toDto(it) }
    }

    fun getTradeById(id: Int): TradeDto {
        return tradeRepository.findByIdOrNull(id)?.let { tradeMapper.toDto(it) }
            ?: throw EntityNotFoundException("Trade not found with id: $id")
    }

    fun getAllTradesByUserId(userId: Int, pageable: Pageable): Page<TradeDto> {
        return tradeRepository.findAllByUserId(userId, pageable).map { tradeMapper.toDto(it) }
    }

    fun getAllPurchaseOffers(pageable: Pageable): Page<TradeOfferDto> {
        return tradeRepository.findAllPurchaseOffers(pageable)
    }

    fun getAllSellOffers(pageable: Pageable): Page<TradeOfferDto> {
        return tradeRepository.findAllSellOffers(pageable)
    }

    fun getSellOfferByResourceId(resourceId: Int): TradeOfferDto {
        return tradeRepository.findSellOfferByResourceId(resourceId) ?: throw EntityNotFoundException("Sell offer not found with resource id: $resourceId")
    }

    fun getPurchaseOfferByResourceId(resourceId: Int): TradeOfferDto {
        return tradeRepository.findPurchaseOfferByResourceId(resourceId) ?: throw EntityNotFoundException("Purchase offer not found with resource id: $resourceId")
    }

    @Transactional
    fun trade(request: TradeRequest, user: User): TradeDto {
        val trade = Trade()
        trade.user = user

        val sell = createTradeItems(request.buy, Operation.SELL, trade)
        val purchase = createTradeItems(request.sell, Operation.BUY, trade)

        val stationBalanceChange = calculateStationBalanceChange(sell, purchase)
        accountService.transferFundsBetweenStationAndUser(user.id, stationBalanceChange)
        storageModuleService.retrieveAndStoreAll(sell, purchase)

        trade.items = ArrayList(sell)
        trade.items.addAll(purchase)
        val savedTrade = tradeRepository.save(trade)

        return tradeMapper.toDto(savedTrade)
    }

    private fun createTradeItems(
        resources: Collection<ResourceIdAmountDto>,
        operation: Operation, trade: Trade
    ): List<TradeItem> {
        return resources.map { dto -> ResourceIdAmount(dto.id, dto.amount) }
            .map { resourceService.toResourceAmount(it) }
            .map { resourceAmount ->
                val offer = getTradeOffer(resourceAmount.resourceId, operation)
                if ((offer.amount ?: 0) < (resourceAmount.amount ?: 0)) {
                    throw IllegalArgumentException()
                }
                TradeItem(trade, resourceAmount, operation, offer.price)
            }
    }

    private fun getTradeOffer(resourceId: Int?, operation: Operation): TradeOfferDto {
        return when (operation) {
            Operation.BUY -> getSellOfferByResourceId(resourceId ?: 0)      // user buys -> station sells
            Operation.SELL -> getPurchaseOfferByResourceId(resourceId ?: 0) // user sells -> station buys
        }
    }

    private fun calculateStationBalanceChange(sell: List<TradeItem>, purchase: List<TradeItem>): Int {
        val income = calculateSumPrice(sell)
        val outcome = calculateSumPrice(purchase)
        return income - outcome
    }

    private fun calculateSumPrice(items: List<TradeItem>): Int {
        return items.sumOf { (it.price ?: 0) * (it.amount ?: 0) }
    }
}
