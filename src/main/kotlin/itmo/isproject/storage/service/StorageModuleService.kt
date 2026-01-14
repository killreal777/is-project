package itmo.isproject.storage.service

import itmo.isproject.storage.dto.StorageModuleDto
import itmo.isproject.shared.resource.dto.ResourceAmountDto
import itmo.isproject.storage.dto.StoredResourceDto
import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.oshai.kotlinlogging.withLoggingContext
import itmo.isproject.storage.mapper.StorageModuleMapper
import itmo.isproject.storage.mapper.ResourceAmountMapper
import itmo.isproject.storage.mapper.StoredResourceMapper
import itmo.isproject.storage.model.StorageModuleFreeSpace
import itmo.isproject.storage.model.StoredResource
import itmo.isproject.storage.model.StoredResourceId
import itmo.isproject.shared.resource.model.ResourceAmountHolder
import itmo.isproject.shared.resource.model.ResourceIdAmountHolder
import itmo.isproject.storage.repository.StorageModuleRepository
import itmo.isproject.storage.repository.StoredResourceRepository
import itmo.isproject.storage.api.StorageService
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

private val logger = KotlinLogging.logger {}

@Service
class StorageModuleService(
    private val storageModuleRepository: StorageModuleRepository,
    private val storageModuleMapper: StorageModuleMapper,
    private val storedResourceRepository: StoredResourceRepository,
    private val storedResourceMapper: StoredResourceMapper,
    private val resourceAmountMapper: ResourceAmountMapper
) : StorageService {

    fun getAllStorageModules(pageable: Pageable): Page<StorageModuleDto> {
        withLoggingContext("page" to pageable.pageNumber.toString()) {
            logger.debug { "Fetching all storage modules" }
        }
        return storageModuleRepository.findAll(pageable).map { storageModuleMapper.toDto(it) }
    }

    fun getStorageModuleById(id: Int): StorageModuleDto {
        withLoggingContext("storageModuleId" to id.toString()) {
            logger.debug { "Fetching storage module by" }
        }
        return storageModuleRepository.findByIdOrNull(id)?.let { storageModuleMapper.toDto(it) }
            ?: throw EntityNotFoundException("Storage module not found with id: $id")
    }

    override fun getAllStoredResources(pageable: Pageable): Page<StoredResourceDto> {
        withLoggingContext("page" to pageable.pageNumber.toString()) {
            logger.debug { "Fetching all stored resources" }
        }
        return storedResourceRepository.findAll(pageable)
            .map { storedResourceMapper.toDto(it) }
    }

    override fun getAllResourcesTotal(pageable: Pageable): Page<ResourceAmountDto> {
        withLoggingContext("page" to pageable.pageNumber.toString()) {
            logger.debug { "Fetching all resources total" }
        }
        return storedResourceRepository.findAllResourceAmountsTotal(pageable)
            .map { resourceAmountMapper.toDto(it) }
    }

    override fun getResourceAmountTotalByResourceId(resourceId: Int): ResourceAmountDto {
        withLoggingContext("resourceId" to resourceId.toString()) {
            logger.debug { "Fetching resource amount total by" }
        }
        return storedResourceRepository.findResourceAmountTotal(resourceId)
            ?.let { resourceAmountMapper.toDto(it) }
            ?: throw EntityNotFoundException("Resource not found in storages with resource id: $resourceId")
    }

    override fun getAllResourcesByStorageId(storageModuleId: Int, pageable: Pageable): Page<ResourceAmountDto> {
        withLoggingContext("storageModuleId" to storageModuleId.toString(), "page" to pageable.pageNumber.toString()) {
            logger.debug { "Fetching all resources by" }
        }
        return storedResourceRepository.findAllByIdStorageModuleId(storageModuleId, pageable)
            .map { resourceAmountMapper.toDto(it) }
    }

    @Transactional
    override fun store(resourceAmount: ResourceAmountHolder) {
        storeAll(listOf(resourceAmount))
    }

    @Transactional
    override fun storeAll(resources: Collection<ResourceAmountHolder>) {
        withLoggingContext("resourcesCount" to resources.size.toString()) {
            logger.info { "Storing resources" }
        }
        val amountTotal = sumResourcesAmount(resources)
        withLoggingContext("amount" to amountTotal.toString()) {
            logger.debug { "Total amount to store" }
        }
        checkFreeSpace(amountTotal)
        val storages = getAvailableStorages()
        for (resourceAmount in resources) {
            storeResourceToStorages(resourceAmount, storages)
        }
        withLoggingContext("resourcesCount" to resources.size.toString()) {
            logger.info { "Resources stored successfully" }
        }
    }

    private fun getAvailableStorages(): Deque<StorageModuleFreeSpace> {
        return ArrayDeque(storageModuleRepository.findAllHavingFreeSpace())
    }

    private fun storeResourceToStorages(
        resourceAmount: ResourceAmountHolder,
        storages: Deque<StorageModuleFreeSpace>
    ) {
        var remainingAmount = resourceAmount.amount ?: 0
        while (remainingAmount > 0) {
            val storageModuleFreeSpace = storages.poll()
            remainingAmount -= storeResourceUpToStorageCapacity(resourceAmount, storageModuleFreeSpace)
            if (storageModuleFreeSpace.freeSpace > 0) {
                storages.offerFirst(storageModuleFreeSpace)
            }
        }
    }

    private fun storeResourceUpToStorageCapacity(
        resourceAmountToStore: ResourceAmountHolder,
        storageModuleFreeSpace: StorageModuleFreeSpace
    ): Int {
        val storageModule = storageModuleFreeSpace.storageModule!!
        val storageModuleId = storageModule.id
        val resourceId = resourceAmountToStore.resourceId!!
        val storedResourceId = StoredResourceId(storageModuleId, resourceId)
        val storedResource = storedResourceRepository.findByIdOrNull(storedResourceId) ?: StoredResource(
            resourceAmountToStore,
            storageModule
        )
        val storeAmount = (resourceAmountToStore.amount ?: 0).coerceAtMost(storageModuleFreeSpace.freeSpace)
        storedResource += storeAmount
        storedResourceRepository.save(storedResource)
        storageModuleFreeSpace -= storeAmount
        return storeAmount
    }

    @Transactional
    override fun retrieveAll(resources: Collection<ResourceAmountHolder>) {
        withLoggingContext("resourcesCount" to resources.size.toString()) {
            logger.info { "Retrieving resources" }
        }
        for (resourceAmount in resources) {
            checkExistenceRequiredResourceAmount(resourceAmount)
        }
        for (resourceAmount in resources) {
            retrieveResourceFromStorages(resourceAmount)
        }
        withLoggingContext("resourcesCount" to resources.size.toString()) {
            logger.info { "Resources retrieved successfully" }
        }
    }

    private fun checkExistenceRequiredResourceAmount(required: ResourceIdAmountHolder) {
        val resourceId = required.resourceId
        val total = storedResourceRepository.findResourceAmountTotal(resourceId)
            ?: throw EntityNotFoundException("Resource not found in storages with resource id: $resourceId")
        if ((total.amount ?: 0) < (required.amount ?: 0)) {
            withLoggingContext(
                "resourceId" to (resourceId?.toString() ?: "null"),
                "required" to (required.amount?.toString() ?: "null"),
                "available" to (total.amount?.toString() ?: "null")
            ) {
                logger.warn { "Insufficient resources" }
            }
            throw IllegalStateException("Insufficient resources")
        }
    }

    private fun retrieveResourceFromStorages(resourceIdAmount: ResourceIdAmountHolder) {
        val resourceId = resourceIdAmount.resourceId
        val storedResources = storedResourceRepository.findAllByIdResourceId(resourceId)

        var remainingAmount = resourceIdAmount.amount ?: 0
        for (storedResource in storedResources) {
            if ((storedResource.amount ?: 0) <= remainingAmount) {
                storedResourceRepository.delete(storedResource)
                remainingAmount -= storedResource.amount ?: 0
            } else {
                storedResource.amount = (storedResource.amount ?: 0) - remainingAmount
                storedResourceRepository.save(storedResource)
                remainingAmount = 0
            }
            if (remainingAmount == 0) {
                break
            }
        }
    }

    @Transactional
    override fun retrieveAndStoreAll(
        retrieve: Collection<ResourceAmountHolder>,
        store: Collection<ResourceAmountHolder>
    ) {
        val requiredSpace = sumResourcesAmount(store) - sumResourcesAmount(retrieve)
        if (requiredSpace > 0) {
            checkFreeSpace(requiredSpace)
        }
        retrieveAll(retrieve)
        storeAll(store)
    }

    private fun sumResourcesAmount(resources: Collection<ResourceIdAmountHolder>): Int {
        return resources.sumOf { it.amount ?: 0 }
    }

    private fun checkFreeSpace(amount: Int) {
        val freeSpace = storageModuleRepository.getTotalFreeSpaceInStorages() ?: 0
        withLoggingContext("required" to amount.toString(), "available" to freeSpace.toString()) {
            logger.debug { "Checking free space" }
        }
        if (amount > freeSpace) {
            withLoggingContext("required" to amount.toString(), "available" to freeSpace.toString()) {
                logger.warn { "Not enough free space" }
            }
            throw IllegalStateException("Not enough free space in storages")
        }
    }
}
