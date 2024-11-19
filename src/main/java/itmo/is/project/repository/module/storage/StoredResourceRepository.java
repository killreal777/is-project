package itmo.is.project.repository.module.storage;

import itmo.is.project.model.module.storage.StoredResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoredResourceRepository extends JpaRepository<StoredResource, Integer> {
}
