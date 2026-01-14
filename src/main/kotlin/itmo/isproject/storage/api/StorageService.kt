package itmo.isproject.storage.api

import itmo.isproject.shared.resource.dto.ResourceAmountDto
import itmo.isproject.shared.resource.model.ResourceAmountHolder
import itmo.isproject.storage.dto.StoredResourceDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.modulith.NamedInterface

@NamedInterface("api")
interface StorageService {

    fun getAllStoredResources(pageable: Pageable): Page<StoredResourceDto>

    fun getAllResourcesTotal(pageable: Pageable): Page<ResourceAmountDto>

    fun getResourceAmountTotalByResourceId(resourceId: Int): ResourceAmountDto

    fun getAllResourcesByStorageId(storageModuleId: Int, pageable: Pageable): Page<ResourceAmountDto>

    fun store(resourceAmount: ResourceAmountHolder)

    fun storeAll(resources: Collection<ResourceAmountHolder>)

    fun retrieveAll(resources: Collection<ResourceAmountHolder>)

    fun retrieveAndStoreAll(
        retrieve: Collection<ResourceAmountHolder>,
        store: Collection<ResourceAmountHolder>
    )
}
