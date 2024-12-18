package itmo.is.project.service.module;

import itmo.is.project.dto.module.dock.DockModuleDto;
import itmo.is.project.dto.module.dock.DockingSpotDto;
import itmo.is.project.mapper.module.dock.DockModuleMapper;
import itmo.is.project.mapper.module.dock.DockingSpotMapper;
import itmo.is.project.model.module.dock.DockingSpot;
import itmo.is.project.model.user.Spaceship;
import itmo.is.project.model.user.User;
import itmo.is.project.repository.module.dock.DockModuleRepository;
import itmo.is.project.repository.module.dock.DockingSpotRepository;
import itmo.is.project.service.user.SpaceshipService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DockModuleService {

    private final DockModuleRepository dockModuleRepository;
    private final DockModuleMapper dockModuleMapper;

    private final DockingSpotRepository dockingSpotRepository;
    private final DockingSpotMapper dockingSpotMapper;

    private final SpaceshipService spaceshipService;

    public Page<DockModuleDto> getAllDockModules(Pageable pageable) {
        return dockModuleRepository.findAll(pageable).map(dockModuleMapper::toDto);
    }

    public DockModuleDto getDockModuleById(Integer id) {
        return dockModuleRepository.findById(id).map(dockModuleMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Dock module not found with id: " + id));
    }

    public Page<DockingSpotDto> getAllDockingSpots(Pageable pageable) {
        return dockingSpotRepository.findAll(pageable).map(dockingSpotMapper::toDto);
    }

    public Page<DockingSpotDto> getAllOccupiedDockingSpots(Pageable pageable) {
        return dockingSpotRepository.findAllByIsOccupiedTrue(pageable).map(dockingSpotMapper::toDto);
    }

    public DockingSpotDto getOccupiedDockingSpotByPilot(User pilot) {
        return dockingSpotRepository.findBySpaceshipPilot(pilot).map(dockingSpotMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Spaceship is not docked"));
    }

    @Transactional
    public DockingSpotDto dock(User user) {
        Spaceship spaceship = spaceshipService.findSpaceshipEntityByPilotId(user.getId());
        if (dockingSpotRepository.existsBySpaceship(spaceship)) {
            throw new IllegalStateException("Spaceship is already docked");
        }
        DockingSpot dockingSpot = dockingSpotRepository.findFirstBySizeAndIsOccupiedFalse(spaceship.getSize())
                .orElseThrow(() -> new IllegalStateException("No suitable free docking spot found"));
        dockingSpot.setSpaceship(spaceship);
        dockingSpot.setIsOccupied(true);
        return dockingSpotMapper.toDto(dockingSpotRepository.save(dockingSpot));
    }

    @Transactional
    public void undock(User user) {
        DockingSpot dockingSpot = dockingSpotRepository.findBySpaceshipPilot(user)
                .orElseThrow(() -> new IllegalStateException("Spaceship is not docked"));
        dockingSpot.setSpaceship(null);
        dockingSpot.setIsOccupied(false);
        dockingSpotRepository.save(dockingSpot);
    }
}
