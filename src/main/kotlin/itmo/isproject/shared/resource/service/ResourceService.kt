package itmo.isproject.shared.resource.service

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.oshai.kotlinlogging.withLoggingContext
import itmo.isproject.shared.resource.model.Resource
import itmo.isproject.shared.resource.model.ResourceAmount
import itmo.isproject.shared.resource.model.ResourceIdAmountHolder
import itmo.isproject.shared.resource.repository.ResourceRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class ResourceService(
    private val resourceRepository: ResourceRepository
) {

    fun toResourceAmount(resourceIdAmountHolder: ResourceIdAmountHolder): ResourceAmount {
        val resource = getResourceById(resourceIdAmountHolder.resourceId)
        return ResourceAmount(resource, resourceIdAmountHolder.amount)
    }

    fun getResourceById(id: Int?): Resource {
        withLoggingContext("resourceId" to (id?.toString() ?: "null")) {
            logger.debug { "Fetching resource by resourceId" }
        }
        return resourceRepository.findByIdOrNull(id ?: 0)
            ?: throw EntityNotFoundException("Resource not found with id: $id")
    }
}
