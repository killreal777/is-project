package itmo.is.project.repository.module.production;

import itmo.is.project.model.module.production.ProductionModule;
import itmo.is.project.repository.module.ModuleRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionModuleRepository extends ModuleRepository<ProductionModule> {
}
