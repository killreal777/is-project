package itmo.isproject.service.trade

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.oshai.kotlinlogging.withLoggingContext
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

private val logger = KotlinLogging.logger {}

@Service
class TradeService(
    private val tradeRepository: TradeRepository,
    private val tradeMapper: TradeMapper,
    private val accountService: AccountService,
    private val storageModuleService: StorageModuleService,
    private val resourceService: ResourceService
) {

    fun getAllTrades(pageable: Pageable): Page<TradeDto> {
        withLoggingContext("page" to pageable.pageNumber.toString(), "size" to pageable.pageSize.toString()) {
            logger.debug { "Fetching all trades" }
        }
        return tradeRepository.findAll(pageable).map { tradeMapper.toDto(it) }
    }

    fun getTradeById(id: Int): TradeDto {
        withLoggingContext("tradeId" to id.toString()) {
            logger.debug { "Fetching trade by id" }
        }
        return tradeRepository.findByIdOrNull(id)?.let { tradeMapper.toDto(it) }
            ?: throw EntityNotFoundException("Trade not found with id: $id")
    }

    fun getAllTradesByUserId(userId: Int, pageable: Pageable): Page<TradeDto> {
        withLoggingContext("userId" to userId.toString(), "page" to pageable.pageNumber.toString()) {
            logger.debug { "Fetching trades for user" }
        }
        return tradeRepository.findAllByUserId(userId, pageable).map { tradeMapper.toDto(it) }
    }

    fun getAllPurchaseOffers(pageable: Pageable): Page<TradeOfferDto> {
        withLoggingContext("page" to pageable.pageNumber.toString()) {
            logger.debug { "Fetching all purchase offers" }
        }
        return tradeRepository.findAllPurchaseOffers(pageable)
    }

    fun getAllSellOffers(pageable: Pageable): Page<TradeOfferDto> {
        withLoggingContext("page" to pageable.pageNumber.toString()) {
            logger.debug { "Fetching all sell offers" }
        }
        return tradeRepository.findAllSellOffers(pageable)
    }

    fun getSellOfferByResourceId(resourceId: Int): TradeOfferDto {
        withLoggingContext("resourceId" to resourceId.toString()) {
            logger.debug { "Fetching sell offer for resource" }
        }
        return tradeRepository.findSellOfferByResourceId(resourceId)
            ?: throw EntityNotFoundException("Sell offer not found with resource id: $resourceId")
    }

    fun getPurchaseOfferByResourceId(resourceId: Int): TradeOfferDto {
        withLoggingContext("resourceId" to resourceId.toString()) {
            logger.debug { "Fetching purchase offer for resource" }
        }
        return tradeRepository.findPurchaseOfferByResourceId(resourceId)
            ?: throw EntityNotFoundException("Purchase offer not found with resource id: $resourceId")
    }

    @Transactional
    fun trade(request: TradeRequest, user: User): TradeDto {
        withLoggingContext("username" to user.usernameInternal, "userId" to (user.id?.toString() ?: "null")) {
            logger.info { "Processing trade request for user" }
        }
        withLoggingContext("buyCount" to request.buy.size.toString(), "sellCount" to request.sell.size.toString()) {
            logger.debug { "Trade request details" }
        }

        val trade = Trade()
        trade.user = user

        val sell = createTradeItems(request.buy, Operation.SELL, trade)
        val purchase = createTradeItems(request.sell, Operation.BUY, trade)

        val stationBalanceChange = calculateStationBalanceChange(sell, purchase)
        withLoggingContext("balanceChange" to stationBalanceChange.toString()) {
            logger.info { "Trade balance change for station" }
        }

        accountService.transferFundsBetweenStationAndUser(user.id, stationBalanceChange)
        storageModuleService.retrieveAndStoreAll(sell, purchase)

        trade.items = ArrayList(sell)
        trade.items.addAll(purchase)
        val savedTrade = tradeRepository.save(trade)

        withLoggingContext(
            "tradeId" to (savedTrade.id?.toString() ?: "null"),
            "username" to user.usernameInternal,
            "balanceChange" to stationBalanceChange.toString()
        ) {
            logger.info { "Trade completed" }
        }
        return tradeMapper.toDto(savedTrade)
    }

    private fun createTradeItems(
        resources: Collection<ResourceIdAmountDto>,
        operation: Operation, trade: Trade
    ): List<TradeItem> {
        withLoggingContext("resourcesCount" to resources.size.toString(), "operation" to operation.toString()) {
            logger.debug { "Creating trade items for operation" }
        }
        return resources.map { dto -> ResourceIdAmount(dto.id, dto.amount) }
            .map { resourceService.toResourceAmount(it) }
            .map { resourceAmount ->
                val offer = getTradeOffer(resourceAmount.resourceId, operation)
                if ((offer.amount ?: 0) < (resourceAmount.amount ?: 0)) {
                    withLoggingContext(
                        "resourceId" to (resourceAmount.resourceId?.toString() ?: "null"),
                        "requested" to (resourceAmount.amount?.toString() ?: "null"),
                        "available" to (offer.amount?.toString() ?: "null")
                    ) {
                        logger.warn { "Insufficient resources for trade" }
                    }
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
        withLoggingContext(
            "income" to income.toString(),
            "outcome" to outcome.toString(),
            "net" to (income - outcome).toString()
        ) {
            logger.debug { "Trade calculation" }
        }
        return income - outcome
    }

    private fun calculateSumPrice(items: List<TradeItem>): Int {
        return items.sumOf { (it.price ?: 0) * (it.amount ?: 0) }
    }
}
