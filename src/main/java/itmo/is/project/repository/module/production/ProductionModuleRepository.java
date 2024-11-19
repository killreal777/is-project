package itmo.is.project.repository.module.production;

import itmo.is.project.model.module.production.ProductionModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionModuleRepository extends JpaRepository<ProductionModule, Integer> {
}
