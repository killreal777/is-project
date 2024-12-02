package itmo.is.project.service.resource;

import itmo.is.project.model.resource.Resource;
import itmo.is.project.model.resource.ResourceAmount;
import itmo.is.project.model.resource.ResourceIdAmountHolder;
import itmo.is.project.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResourceService {
    private final ResourceRepository resourceRepository;

    public ResourceAmount toResourceAmount(ResourceIdAmountHolder resourceIdAmountHolder) {
        Resource resource = resourceRepository.findById(resourceIdAmountHolder.getResourceId()).orElseThrow();
        return new ResourceAmount(resource, resourceIdAmountHolder.getAmount());
    }
}
