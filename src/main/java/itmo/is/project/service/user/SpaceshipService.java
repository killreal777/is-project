package itmo.is.project.service.user;

import itmo.is.project.dto.user.CreateSpaceshipRequest;
import itmo.is.project.dto.user.SpaceshipDto;
import itmo.is.project.mapper.user.SpaceshipMapper;
import itmo.is.project.model.user.Spaceship;
import itmo.is.project.model.user.User;
import itmo.is.project.repository.user.SpaceshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class SpaceshipService {
    private final SpaceshipRepository spaceshipRepository;
    private final SpaceshipMapper spaceshipMapper;

    public Page<SpaceshipDto> findAllSpaceships(Pageable pageable) {
        return spaceshipRepository.findAll(pageable).map(spaceshipMapper::toDto);
    }

    public SpaceshipDto findSpaceshipById(Integer id) {
        return spaceshipRepository.findById(id)
                .map(spaceshipMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException("Spaceship not found with id: " + id));
    }

    public SpaceshipDto findSpaceshipByPilotId(Integer pilotId) {
        return spaceshipRepository.findByPilotId(pilotId)
                .map(spaceshipMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException("Spaceship not found with pilot id: " + pilotId));
    }

    public Spaceship findSpaceshipEntityByPilotId(Integer pilotId) {
        return spaceshipRepository.findByPilotId(pilotId)
                .orElseThrow(() -> new NoSuchElementException("Spaceship not found with pilot id: " + pilotId));
    }

    public SpaceshipDto findSpaceshipByPilotUsername(String pilotUsername) {
        return spaceshipRepository.findByPilotUsername(pilotUsername)
                .map(spaceshipMapper::toDto)
                .orElseThrow(() ->
                        new NoSuchElementException("Spaceship not found with pilot username: " + pilotUsername)
                );
    }

    public SpaceshipDto createSpaceship(CreateSpaceshipRequest request, User owner) {
        Spaceship spaceship = spaceshipMapper.toEntity(request);
        spaceship.setPilot(owner);
        return spaceshipMapper.toDto(spaceshipRepository.save(spaceship));
    }
}
