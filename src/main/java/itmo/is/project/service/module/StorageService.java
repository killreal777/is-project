package itmo.is.project.service.module;

import itmo.is.project.dto.resource.ResourceAmountDto;
import itmo.is.project.dto.resource.StoredResourceDto;
import itmo.is.project.mapper.resource.ResourceAmountMapper;
import itmo.is.project.mapper.resource.ResourceMapper;
import itmo.is.project.mapper.resource.StoredResourceMapper;
import itmo.is.project.model.module.storage.StorageModule;
import itmo.is.project.model.module.storage.StoredResource;
import itmo.is.project.model.resource.Resource;
import itmo.is.project.model.resource.ResourceAmount;
import itmo.is.project.repository.ResourceRepository;
import itmo.is.project.repository.module.storage.StorageModuleRepository;
import itmo.is.project.repository.module.storage.StoredResourceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Queue;

@Service
@RequiredArgsConstructor
public class StorageService {
    private final StoredResourceRepository storedResourceRepository;
    private final ResourceMapper resourceMapper;
    private final ResourceAmountMapper resourceAmountMapper;
    private final StoredResourceMapper storedResourceMapper;
    private final ResourceRepository resourceRepository;

    private final StorageModuleRepository storageModuleRepository;

    public Page<StoredResourceDto> getAllStoredResources(Pageable pageable) {
        return storedResourceRepository.findAll(pageable)
                .map(storedResourceMapper::toDto);
    }

    public Page<ResourceAmountDto> getAllResourceAmountsTotal(Pageable pageable) {
        return storedResourceRepository.findAllResourceAmountsTotal(pageable)
                .map(resourceAmountMapper::toDto);
    }

    public ResourceAmountDto getResourceAmountTotal(Integer resourceId) {
        return storedResourceRepository.findResourceAmountTotal(resourceId)
                .map(resourceAmountMapper::toDto).orElseThrow();
    }

    public Page<ResourceAmountDto> getAllResourceAmountsByStorageId(Integer storageModuleId, Pageable pageable) {
        return storedResourceRepository.findAllByStorageId(storageModuleId, pageable)
                .map(storedResource -> resourceAmountMapper.toDto(storedResource.getResourceAmount()));
    }

    @Transactional
    public void store(Integer resourceId, Integer amount) {
        ResourceAmount resourceAmount = toResourceAmount(resourceId, amount);
        if (resourceAmount.getAmount() > storageModuleRepository.getTotalFreeSpaceInStorages()) {
            throw new IllegalStateException();
        }
        for (StorageModule storageModule : storageModuleRepository.findAll()) {
            store(resourceAmount, storageModule);
            if (resourceAmount.getAmount() == 0) {
                break;
            }
        }
    }

    private ResourceAmount toResourceAmount(Integer resourceId, Integer amount) {
        Resource resource = resourceRepository.findById(resourceId).orElseThrow();
        return new ResourceAmount(resource, amount);
    }

    private void store(ResourceAmount resourceAmount, StorageModule storageModule) {
        int space = calculateFreeSpace(storageModule);
        if (space == 0) {
            return;
        }
        int amountToStore = Math.min(space, resourceAmount.getAmount());

        storageModule.getStoredResources().stream()
                .filter(s -> s.getResource().equals(resourceAmount.getResource()))
                .findFirst()
                .ifPresentOrElse(
                        storedResource -> storedResource.subtract(amountToStore),
                        () -> storageModule.addNewResource(resourceAmount.withAmount(amountToStore))
                );
        resourceAmount.subtract(amountToStore);
        System.out.println("Store " + amountToStore + " to storage " + storageModule.getId());
    }

    public void retrieve(Integer resourceId, Integer amount) {

    }

    private Integer calculateFreeSpace(StorageModule storageModule) {
        int usedSpace = storageModule.getStoredResources().stream()
                .mapToInt(StoredResource::getAmount)
                .sum();
        return storageModule.getBlueprint().getCapacity() - usedSpace;
    }
}
