package itmo.isproject.repository.module.storage

import itmo.isproject.model.module.storage.StoredResource
import itmo.isproject.model.module.storage.StoredResourceId
import itmo.isproject.model.resource.ResourceAmount
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface StoredResourceRepository : JpaRepository<StoredResource, StoredResourceId> {

    @Query("""
            SELECT new itmo.isproject.model.resource.ResourceAmount(sr.resource.id, sr.resource.name, SUM(sr.amount))
            FROM StoredResource sr GROUP BY sr.resource.id, sr.resource.name
            """)
    fun findAllResourceAmountsTotal(pageable: Pageable): Page<ResourceAmount>

    @Query("""
            SELECT new itmo.isproject.model.resource.ResourceAmount(sr.resource.id, sr.resource.name, SUM(sr.amount))
            FROM StoredResource sr WHERE sr.resource.id = :resourceId GROUP BY sr.resource.id, sr.resource.name
            """)
    fun findResourceAmountTotal(resourceId: Int?): ResourceAmount?

    fun findAllByIdStorageModuleId(storageId: Int?, pageable: Pageable): Page<StoredResource>

    fun findAllByIdResourceId(resourceId: Int?): List<StoredResource>
}
