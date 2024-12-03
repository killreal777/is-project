package itmo.is.project.service.resource;

import itmo.is.project.model.resource.Resource;
import itmo.is.project.model.resource.ResourceAmount;
import itmo.is.project.model.resource.ResourceIdAmountHolder;
import itmo.is.project.repository.ResourceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResourceService {
    private final ResourceRepository resourceRepository;

    public ResourceAmount toResourceAmount(ResourceIdAmountHolder resourceIdAmountHolder) {
        Resource resource = getResourceById(resourceIdAmountHolder.getResourceId());
        return new ResourceAmount(resource, resourceIdAmountHolder.getAmount());
    }

    public Resource getResourceById(Integer id) {
        return resourceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Resource not found with id: " + id));
    }
}
