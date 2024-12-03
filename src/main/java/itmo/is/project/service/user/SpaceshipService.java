package itmo.is.project.service.user;

import itmo.is.project.dto.user.CreateSpaceshipRequest;
import itmo.is.project.dto.user.SpaceshipDto;
import itmo.is.project.dto.user.UpdateSpaceshipRequest;
import itmo.is.project.mapper.user.SpaceshipMapper;
import itmo.is.project.model.user.Spaceship;
import itmo.is.project.model.user.User;
import itmo.is.project.repository.user.SpaceshipRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SpaceshipService {
    private final SpaceshipRepository spaceshipRepository;
    private final SpaceshipMapper spaceshipMapper;

    public Page<SpaceshipDto> findAllSpaceships(Pageable pageable) {
        return spaceshipRepository.findAll(pageable).map(spaceshipMapper::toDto);
    }

    public SpaceshipDto findSpaceshipById(Integer id) {
        return spaceshipRepository.findById(id).map(spaceshipMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Spaceship not found with id: " + id));
    }

    public SpaceshipDto findSpaceshipByPilotId(Integer pilotId) {
        return spaceshipMapper.toDto(findSpaceshipEntityByPilotId(pilotId));
    }

    public Spaceship findSpaceshipEntityByPilotId(Integer pilotId) {
        return spaceshipRepository.findByPilotId(pilotId)
                .orElseThrow(() -> new EntityNotFoundException("Spaceship not found with pilot id: " + pilotId));
    }

    @Transactional
    public SpaceshipDto createSpaceship(CreateSpaceshipRequest request, User owner) {
        spaceshipRepository.findByPilotId(owner.getId()).ifPresent(spaceship -> {
            throw new IllegalStateException("Spaceship already exists with id: " + spaceship.getId());
        });
        Spaceship spaceship = spaceshipMapper.toEntity(request);
        spaceship.setPilot(owner);
        spaceship = spaceshipRepository.save(spaceship);
        return spaceshipMapper.toDto(spaceship);
    }

    @Transactional
    public SpaceshipDto updateSpaceship(UpdateSpaceshipRequest request, User owner) {
        Spaceship spaceship = findSpaceshipEntityByPilotId(owner.getId());
        spaceship.setSize(request.size());
        spaceship = spaceshipRepository.save(spaceship);
        return spaceshipMapper.toDto(spaceship);
    }
}
