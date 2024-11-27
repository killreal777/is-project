package itmo.is.project.repository.module.storage;

import itmo.is.project.model.module.storage.StoredResource;
import itmo.is.project.model.resource.ResourceAmount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface StoredResourceRepository extends JpaRepository<StoredResource, StoredResource.CompositeKey> {

    @Query("""
            SELECT new itmo.is.project.model.resource.ResourceAmount(sr.resource.id, sr.resource.name, SUM(sr.amount))
            FROM StoredResource sr GROUP BY sr.resource.id, sr.resource.name
            """)
    Page<ResourceAmount> findAllResourceAmountsTotal(Pageable pageable);

    @Query("""
            SELECT new itmo.is.project.model.resource.ResourceAmount(sr.resource.id, sr.resource.name, SUM(sr.amount))
            FROM StoredResource sr WHERE sr.resource.id = :resourceId GROUP BY sr.resource.id, sr.resource.name
            """)
    Optional<ResourceAmount> findResourceAmountTotal(Integer resourceId);

    Page<StoredResource> findAllByStorageId(Integer storageId, Pageable pageable);

    List<StoredResource> findAllByResourceId(Integer resourceId);
}
