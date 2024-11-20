package itmo.is.project.repository.module.production;

import itmo.is.project.model.module.production.ProductionModuleBlueprint;
import itmo.is.project.repository.module.ModuleBlueprintRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionModuleBlueprintRepository extends ModuleBlueprintRepository<ProductionModuleBlueprint> {
}
