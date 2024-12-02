package itmo.is.project.service.module;

import itmo.is.project.dto.module.dock.DockModuleBlueprintDto;
import itmo.is.project.dto.module.dock.DockModuleDto;
import itmo.is.project.dto.module.dock.DockingSpotDto;
import itmo.is.project.mapper.module.dock.DockModuleBlueprintMapper;
import itmo.is.project.mapper.module.dock.DockModuleMapper;
import itmo.is.project.mapper.module.dock.DockingSpotMapper;
import itmo.is.project.model.module.dock.DockingSpot;
import itmo.is.project.model.user.Spaceship;
import itmo.is.project.model.user.User;
import itmo.is.project.repository.module.dock.DockModuleBlueprintRepository;
import itmo.is.project.repository.module.dock.DockModuleRepository;
import itmo.is.project.repository.module.dock.DockingSpotRepository;
import itmo.is.project.service.user.SpaceshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DockModuleService {

    private final DockModuleBlueprintRepository dockModuleBlueprintRepository;
    private final DockModuleBlueprintMapper dockModuleBlueprintMapper;

    private final DockModuleRepository dockModuleRepository;
    private final DockModuleMapper dockModuleMapper;

    private final DockingSpotRepository dockingSpotRepository;
    private final DockingSpotMapper dockingSpotMapper;
    private final SpaceshipService spaceshipService;

    public Page<DockModuleBlueprintDto> findAllBlueprints(Pageable pageable) {
        return dockModuleBlueprintRepository.findAll(pageable)
                .map(dockModuleBlueprintMapper::toDto);
    }

    public Page<DockModuleDto> findAllModules(Pageable pageable) {
        return dockModuleRepository.findAll(pageable).map(dockModuleMapper::toDto);
    }

    public Page<DockingSpotDto> getAllDockingSpots(Pageable pageable) {
        return dockingSpotRepository.findAll(pageable).map(dockingSpotMapper::toDto);
    }

    public Page<DockingSpotDto> getAllOccupiedDockingSpots(Pageable pageable) {
        return dockingSpotRepository.findAllByIsOccupiedTrue(pageable).map(dockingSpotMapper::toDto);
    }

    public DockingSpotDto dock(User user) {
        Spaceship spaceship = spaceshipService.findSpaceshipEntityByPilotId(user.getId());
        if (dockingSpotRepository.existsBySpaceship(spaceship)) {
            throw new IllegalStateException("Spaceship is already docked");
        }
        DockingSpot dockingSpot = dockingSpotRepository.findFirstBySizeAndIsOccupiedFalse(spaceship.getSize())
                .orElseThrow(() -> new IllegalStateException("DockingSpot not found"));
        dockingSpot.setSpaceship(spaceship);
        dockingSpot.setIsOccupied(true);
        return dockingSpotMapper.toDto(dockingSpotRepository.save(dockingSpot));
    }

    public void undock(User user) {
        DockingSpot dockingSpot = dockingSpotRepository.findBySpaceshipPilot(user)
                .orElseThrow(() -> new IllegalStateException("DockingSpot not found"));
        dockingSpot.setSpaceship(null);
        dockingSpot.setIsOccupied(false);
        dockingSpotRepository.save(dockingSpot);
    }
}
