package itmo.isproject.model.module.storage

import itmo.isproject.model.EmbeddedIdEntity
import itmo.isproject.model.resource.Resource
import itmo.isproject.model.resource.ResourceAmountHolder
import jakarta.persistence.*
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import java.io.Serializable

@Entity
@Table(name = "stored_resource")
class StoredResource(

    @MapsId("storageModuleId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storage_id", referencedColumnName = "id")
    var storageModule: StorageModule? = null,

    @MapsId("resourceId")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resource_id", referencedColumnName = "id")
    override var resource: Resource? = null,

    @NotNull
    @Min(1)
    @Column(name = "amount", nullable = false)
    override var amount: Int? = 0

) : EmbeddedIdEntity<StoredResourceId>(), ResourceAmountHolder {

    constructor(
        resourceAmount: ResourceAmountHolder,
        storageModule: StorageModule
    ) : this(
        resource = resourceAmount.resource,
        amount = resourceAmount.amount,
        storageModule = storageModule
    ) {
        id = StoredResourceId(storageModule.id, resource?.id)
    }
}

@Embeddable
data class StoredResourceId(
    val storageModuleId: Int?,
    val resourceId: Int?
) : Serializable