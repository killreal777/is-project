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
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;

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
        if (amount > storageModuleRepository.getTotalFreeSpaceInStorages()) {
            throw new IllegalStateException();
        }
        Resource resource = resourceRepository.findById(resourceId).orElseThrow();
        List<Pair<StorageModule, Integer>> storageFreeSpaces = storageModuleRepository.findAllHavingFreeSpace();
        int remainingAmount = amount;

        for (Pair<StorageModule, Integer> storageFreeSpace : storageFreeSpaces) {
            StorageModule storageModule = storageFreeSpace.getFirst();
            Integer freeSpace = storageFreeSpace.getSecond();
            StoredResource.CompositeKey id = new StoredResource.CompositeKey(storageModule.getId(), resourceId);
            StoredResource storedResource = storedResourceRepository.findById(id).orElseGet(() ->
                    new StoredResource(id, storageModule, resource, 0)
            );
            int storedAmount = Math.min(remainingAmount, freeSpace);
            storedResource.setAmount(storedResource.getAmount() + storedAmount);
            storedResourceRepository.save(storedResource);
            remainingAmount -= storedAmount;
            if (remainingAmount == 0) {
                break;
            }
        }
    }

    public void retrieve(Integer resourceId, Integer amount) {
        ResourceAmount totalAmount = storedResourceRepository.findResourceAmountTotal(resourceId).orElseThrow();
        if (totalAmount.getAmount() < amount) {
            throw new IllegalStateException();
        }
        List<StoredResource> storedResources = storedResourceRepository.findAllByResourceId(resourceId);
        int remainingAmount = amount;
        for (StoredResource storedResource : storedResources) {
            if (storedResource.getAmount() <= remainingAmount) {
                storedResourceRepository.delete(storedResource);
                remainingAmount -= storedResource.getAmount();
            } else {
                storedResource.setAmount(storedResource.getAmount() - remainingAmount);
                storedResourceRepository.save(storedResource);
                remainingAmount = 0;
            }
            if (remainingAmount == 0) {
                break;
            }
        }
    }
}
