package itmo.is.project.service.module;

import itmo.is.project.dto.module.storage.StorageModuleDto;
import itmo.is.project.dto.resource.ResourceAmountDto;
import itmo.is.project.dto.module.storage.StoredResourceDto;
import itmo.is.project.mapper.module.storage.StorageModuleMapper;
import itmo.is.project.mapper.resource.ResourceAmountMapper;
import itmo.is.project.mapper.module.storage.StoredResourceMapper;
import itmo.is.project.model.module.storage.StorageModuleFreeSpace;
import itmo.is.project.model.module.storage.StoredResource;
import itmo.is.project.model.resource.ResourceAmount;
import itmo.is.project.model.resource.ResourceAmountHolder;
import itmo.is.project.model.resource.ResourceIdAmountHolder;
import itmo.is.project.repository.module.storage.StorageModuleRepository;
import itmo.is.project.repository.module.storage.StoredResourceRepository;
import itmo.is.project.service.resource.ResourceService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class StorageModuleService {

    private final StorageModuleRepository storageModuleRepository;
    private final StorageModuleMapper storageModuleMapper;

    private final StoredResourceRepository storedResourceRepository;
    private final StoredResourceMapper storedResourceMapper;
    private final ResourceAmountMapper resourceAmountMapper;

    private final ResourceService resourceService;

    public Page<StorageModuleDto> getAllStorageModules(Pageable pageable) {
        return storageModuleRepository.findAll(pageable).map(storageModuleMapper::toDto);
    }

    public StorageModuleDto getStorageModuleById(Integer id) {
        return storageModuleRepository.findById(id).map(storageModuleMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Storage module not found with id: " + id));
    }

    public Page<StoredResourceDto> getAllStoredResources(Pageable pageable) {
        return storedResourceRepository.findAll(pageable)
                .map(storedResourceMapper::toDto);
    }

    public Page<ResourceAmountDto> getAllResourcesTotal(Pageable pageable) {
        return storedResourceRepository.findAllResourceAmountsTotal(pageable)
                .map(resourceAmountMapper::toDto);
    }

    public ResourceAmountDto getResourceAmountTotalByResourceId(Integer resourceId) {
        return storedResourceRepository.findResourceAmountTotal(resourceId)
                .map(resourceAmountMapper::toDto)
                .orElseThrow(() ->
                        new EntityNotFoundException("Resource not found in storages with resource id: " + resourceId)
                );
    }

    public Page<ResourceAmountDto> getAllResourcesByStorageId(Integer storageModuleId, Pageable pageable) {
        return storedResourceRepository.findAllByIdStorageModuleId(storageModuleId, pageable)
                .map(resourceAmountMapper::toDto);
    }


    @Transactional
    public void storeById(ResourceIdAmountHolder resourceIdAmount) {
        store(resourceService.toResourceAmount(resourceIdAmount));
    }

    @Transactional
    public void store(ResourceAmountHolder resourceAmount) {
        storeAll(List.of(resourceAmount));
    }

    @Transactional
    public void storeAllById(Collection<? extends ResourceIdAmountHolder> resources) {
        storeAll(resources.stream().map(resourceService::toResourceAmount).toList());
    }

    @Transactional
    public void storeAll(Collection<? extends ResourceAmountHolder> resources) {
        int amountTotal = sumResourcesAmount(resources);
        checkFreeSpace(amountTotal);
        Deque<StorageModuleFreeSpace> storages = getAvailableStorages();
        for (ResourceAmountHolder resourceAmount : resources) {
            storeResourceToStorages(resourceAmount, storages);
        }
    }

    private Deque<StorageModuleFreeSpace> getAvailableStorages() {
        return new ArrayDeque<>(storageModuleRepository.findAllHavingFreeSpace());
    }

    private void storeResourceToStorages(
            ResourceAmountHolder resourceAmount,
            Deque<StorageModuleFreeSpace> storages
    ) {
        int remainingAmount = resourceAmount.getAmount();
        while (remainingAmount > 0) {
            StorageModuleFreeSpace storageModuleFreeSpace = storages.poll();
            remainingAmount -= storeResourceUpToStorageCapacity(resourceAmount, storageModuleFreeSpace);
            if (storageModuleFreeSpace.getFreeSpace() > 0) {
                storages.offerFirst(storageModuleFreeSpace);
            }
        }
    }

    private int storeResourceUpToStorageCapacity(
            ResourceAmountHolder resourceAmountToStore,
            StorageModuleFreeSpace storageModuleFreeSpace
    ) {
        StoredResource.CompositeKey id = new StoredResource.CompositeKey(
                storageModuleFreeSpace.getStorageModuleId(), resourceAmountToStore.getResourceId()
        );
        StoredResource storedResource = storedResourceRepository.findById(id).orElseGet(() ->
                new StoredResource(id, storageModuleFreeSpace.getStorageModule(), resourceAmountToStore.getResource(), 0)
        );
        int storeAmount = Math.min(resourceAmountToStore.getAmount(), storageModuleFreeSpace.getFreeSpace());
        storedResource.add(storeAmount);
        storedResourceRepository.save(storedResource);
        storageModuleFreeSpace.sub(storeAmount);
        return storeAmount;
    }


    @Transactional
    public void retrieveById(ResourceIdAmountHolder resourceIdAmount) {
        retrieve(resourceService.toResourceAmount(resourceIdAmount));
    }

    @Transactional
    public void retrieve(ResourceAmountHolder resourceAmount) {
        retrieveAll(List.of(resourceAmount));
    }

    @Transactional
    public void retrieveAllById(Collection<? extends ResourceIdAmountHolder> resources) {
        retrieveAll(resources.stream().map(resourceService::toResourceAmount).toList());
    }

    @Transactional
    public void retrieveAll(Collection<? extends ResourceAmountHolder> resources) {
        for (ResourceAmountHolder resourceAmount : resources) {
            checkExistenceRequiredResourceAmount(resourceAmount);
        }
        for (ResourceAmountHolder resourceAmount : resources) {
            retrieveResourceFromStorages(resourceAmount);
        }
    }

    private void checkExistenceRequiredResourceAmount(ResourceIdAmountHolder required) {
        Integer resourceId = required.getResourceId();
        ResourceAmount total = storedResourceRepository.findResourceAmountTotal(resourceId).orElseThrow();
        if (total.getAmount() < required.getAmount()) {
            throw new IllegalStateException("Insufficient resources");
        }
    }

    private void retrieveResourceFromStorages(ResourceIdAmountHolder resourceIdAmount) {
        Integer resourceId = resourceIdAmount.getResourceId();
        List<StoredResource> storedResources = storedResourceRepository.findAllByIdResourceId(resourceId);

        int remainingAmount = resourceIdAmount.getAmount();
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


    @Transactional
    public void retrieveAndStoreAllById(
            Collection<? extends ResourceIdAmountHolder> retrieve,
            Collection<? extends ResourceIdAmountHolder> store
    ) {
        retrieveAndStoreAll(
                retrieve.stream().map(resourceService::toResourceAmount).toList(),
                store.stream().map(resourceService::toResourceAmount).toList()
        );
    }

    @Transactional
    public void retrieveAndStoreAll(
            Collection<? extends ResourceAmountHolder> retrieve,
            Collection<? extends ResourceAmountHolder> store
    ) {
        int requiredSpace = sumResourcesAmount(store) - sumResourcesAmount(retrieve);
        if (requiredSpace > 0) {
            checkFreeSpace(requiredSpace);
        }
        retrieveAll(retrieve);
        storeAll(store);
    }


    private int sumResourcesAmount(Collection<? extends ResourceIdAmountHolder> resources) {
        return resources.stream()
                .mapToInt(ResourceIdAmountHolder::getAmount)
                .sum();
    }

    private void checkFreeSpace(int amount) {
        int freeSpace = storageModuleRepository.getTotalFreeSpaceInStorages();
        if (amount > freeSpace) {
            throw new IllegalStateException("Not enough free space in storages");
        }
    }
}
