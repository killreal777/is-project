package itmo.is.project.service.module;

import itmo.is.project.dto.module.BuildModuleRequest;
import itmo.is.project.mapper.EntityMapper;
import itmo.is.project.model.module.Module;
import itmo.is.project.model.module.ModuleBlueprint;
import itmo.is.project.repository.module.ModuleBlueprintRepository;
import itmo.is.project.repository.module.ModuleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.function.Supplier;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ModuleService<M extends Module<B>, B extends ModuleBlueprint, ModuleDto, BlueprintDto> {
    protected final StorageService storageService;

    protected final ModuleBlueprintRepository<B> moduleBlueprintRepository;
    protected final EntityMapper<BlueprintDto, B> moduleBlueprintMapper;

    protected final ModuleRepository<M> moduleRepository;
    protected final EntityMapper<ModuleDto, M> moduleMapper;
    private final Supplier<M> moduleConstructor;

    public Page<ModuleDto> findAllModules(Pageable pageable) {
        return moduleRepository.findAll(pageable).map(moduleMapper::toDto);
    }

    public ModuleDto findModuleById(Integer id) {
        return moduleRepository.findById(id).map(moduleMapper::toDto).orElse(null);
    }

    public Page<BlueprintDto> findAllBlueprints(Pageable pageable) {
        return moduleBlueprintRepository.findAll(pageable)
                .map(moduleBlueprintMapper::toDto);
    }

    public ModuleDto buildModule(BuildModuleRequest request) {
        B blueprint = moduleBlueprintRepository.findById(request.blueprintId()).orElseThrow();
        M module = moduleConstructor.get();
        module.setBlueprint(blueprint);
        module = moduleRepository.save(module);
        return moduleMapper.toDto(module);
    }
}