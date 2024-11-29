package itmo.is.project.service.module;

import itmo.is.project.dto.resource.ResourceAmountDto;
import itmo.is.project.dto.resource.StoredResourceDto;
import itmo.is.project.mapper.resource.ResourceAmountMapper;
import itmo.is.project.mapper.resource.ResourceMapper;
import itmo.is.project.mapper.resource.StoredResourceMapper;
import itmo.is.project.model.module.storage.StorageModuleFreeSpace;
import itmo.is.project.model.module.storage.StoredResource;
import itmo.is.project.model.resource.Resource;
import itmo.is.project.model.resource.ResourceAmount;
import itmo.is.project.model.resource.ResourceAmountHolder;
import itmo.is.project.model.resource.ResourceIdAmount;
import itmo.is.project.repository.ResourceRepository;
import itmo.is.project.repository.module.storage.StorageModuleRepository;
import itmo.is.project.repository.module.storage.StoredResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
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

    public int getTotalFreeSpace() {
        return storageModuleRepository.getTotalFreeSpaceInStorages();
    }


    private void checkFreeSpace(int amount) {
        int freeSpace = storageModuleRepository.getTotalFreeSpaceInStorages();
        if (amount > freeSpace) {
            throw new IllegalStateException();
        }
    }

    private Deque<StorageModuleFreeSpace> getAvailableStorages() {
        return new ArrayDeque<>(
                storageModuleRepository.findAllHavingFreeSpace().stream()
                        .map(pair -> new StorageModuleFreeSpace(pair.getFirst(), pair.getSecond()))
                        .toList()
        );
    }

    private ResourceAmount toResourceAmount(ResourceIdAmount resourceIdAmount) {
        Resource resource = resourceRepository.findById(resourceIdAmount.getId()).orElseThrow();
        return new ResourceAmount(resource, resourceIdAmount.getAmount());
    }


    public void storeAll(Collection<ResourceIdAmount> resources) {
        int amountTotal = sumResourcesAmount(resources);
        checkFreeSpace(amountTotal);

        Deque<StorageModuleFreeSpace> storages = getAvailableStorages();

        for (ResourceIdAmount resourceIdAmount : resources) {
            storeAllAmountToStorages(toResourceAmount(resourceIdAmount), storages);
        }
    }

    public void storeById(ResourceIdAmount resourceIdAmount) {
        checkFreeSpace(resourceIdAmount.getAmount());
        Deque<StorageModuleFreeSpace> storages = getAvailableStorages();
        storeAllAmountToStorages(toResourceAmount(resourceIdAmount), storages);
    }

    public void store(ResourceAmountHolder resourceAmount) {
        checkFreeSpace(resourceAmount.getAmount());
        Deque<StorageModuleFreeSpace> storages = getAvailableStorages();
        storeAllAmountToStorages(resourceAmount.getResourceAmount(), storages);
    }

    private void storeAllAmountToStorages(ResourceAmount resourceAmount, Deque<StorageModuleFreeSpace> storages) {
        while (resourceAmount.getAmount() > 0) {
            StorageModuleFreeSpace storage = storages.poll();
            storeResourceUpToStorageCapacity(resourceAmount, storage);
            if (storage.getFreeSpace() > 0) {
                storages.offerFirst(storage);
            }
        }
    }

    private void storeResourceUpToStorageCapacity(ResourceAmount resourceAmount, StorageModuleFreeSpace storage) {
        StoredResource.CompositeKey id = new StoredResource.CompositeKey(
                storage.getStorageModule().getId(), resourceAmount.getResource().getId()
        );
        StoredResource storedResource = storedResourceRepository.findById(id).orElseGet(() ->
                new StoredResource(id, storage.getStorageModule(), resourceAmount.getResource(), 0)
        );
        int storeAmount = Math.min(resourceAmount.getAmount(), storage.getFreeSpace());
        storedResource.setAmount(storedResource.getAmount() + storeAmount);
        storedResourceRepository.save(storedResource);
        resourceAmount.setAmount(resourceAmount.getAmount() - storeAmount);
        storage.setFreeSpace(storage.getFreeSpace() - storeAmount);
    }


    public void retrieveAllById(Collection<ResourceIdAmount> resources) {
        for (ResourceIdAmount resourceIdAmount : resources) {
            checkExistenceRequiredResourceAmount(resourceIdAmount);
        }
        for (ResourceIdAmount resourceIdAmount : resources) {
            retrieveFromStorages(toResourceAmount(resourceIdAmount));
        }
    }

    public void retrieveAll(Collection<? extends ResourceAmountHolder> resources) {
        for (ResourceAmountHolder resource : resources) {
            checkExistenceRequiredResourceAmount(resource.getResourceIdAmount());
        }
        for (ResourceAmountHolder resource : resources) {
            retrieveFromStorages(resource.getResourceAmount());
        }
    }

    public void retrieve(ResourceIdAmount resourceIdAmount) {
        checkExistenceRequiredResourceAmount(resourceIdAmount);
        retrieveFromStorages(toResourceAmount(resourceIdAmount));
    }

    private void retrieveFromStorages(ResourceAmount resourceAmount) {
        Integer resourceId = resourceAmount.getResource().getId();
        List<StoredResource> storedResources = storedResourceRepository.findAllByResourceId(resourceId);

        int remainingAmount = resourceAmount.getAmount();
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

    private void checkExistenceRequiredResourceAmount(ResourceIdAmount required) {
        Integer id = required.getId();
        ResourceAmount total = storedResourceRepository.findResourceAmountTotal(id).orElseThrow();
        if (total.getAmount() < required.getAmount()) {
            throw new IllegalStateException();
        }
    }

    public void retrieveAndStoreAll(Collection<ResourceIdAmount> retrieve, Collection<ResourceIdAmount> store) {
        int requiredSpace = sumResourcesAmount(store) - sumResourcesAmount(retrieve);
        if (requiredSpace > 0) {
            checkFreeSpace(requiredSpace);
        }
        retrieveAllById(retrieve);
        storeAll(store);
    }

    private int sumResourcesAmount(Collection<ResourceIdAmount> resources) {
        return resources.stream()
                .mapToInt(ResourceIdAmount::getAmount)
                .sum();
    }

}
