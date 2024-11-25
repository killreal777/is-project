package itmo.is.project.service.module.production;

import itmo.is.project.dto.ProductionModuleDto;
import itmo.is.project.mapper.module.ProductionModuleMapper;
import itmo.is.project.repository.module.production.ProductionModuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductionModuleService {
    private final ProductionModuleRepository productionModuleRepository;
    private final ProductionModuleMapper productionModuleMapper;

    public Page<ProductionModuleDto> findAllModules(Pageable pageable) {
        return productionModuleRepository.findAll(pageable).map(productionModuleMapper::toDto);
    }

    public ProductionModuleDto findModuleById(Integer id) {
        return productionModuleRepository.findById(id).map(productionModuleMapper::toDto).orElse(null);
    }
}
