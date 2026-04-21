package nl.novi.deepwrench42.services;

import nl.novi.deepwrench42.dtos.engineType.EngineTypeRequestDTO;
import nl.novi.deepwrench42.dtos.engineType.EngineTypeResponseDTO;
import nl.novi.deepwrench42.entities.EngineTypeEntity;
import nl.novi.deepwrench42.exceptions.ForeignKeyViolationException;
import nl.novi.deepwrench42.exceptions.RecordNotFoundException;
import nl.novi.deepwrench42.mappers.EngineTypeDTOMapper;
import nl.novi.deepwrench42.repository.AircraftRepository;
import nl.novi.deepwrench42.repository.EngineTypeRepository;
import nl.novi.deepwrench42.repository.ToolKitRepository;
import nl.novi.deepwrench42.repository.ToolRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EngineTypeService {
    private final EngineTypeRepository engineTypeRepository;
    private final EngineTypeDTOMapper engineTypeDTOMapper;
    private final AircraftRepository aircraftRepository;
    private final ToolRepository toolRepository;
    private final ToolKitRepository toolKitRepository;

    public EngineTypeService(EngineTypeRepository engineTypeRepository, EngineTypeDTOMapper engineTypeDTOMapper, AircraftRepository aircraftRepository, ToolRepository toolRepository, ToolKitRepository toolKitRepository) {
        this.engineTypeRepository = engineTypeRepository;
        this.engineTypeDTOMapper = engineTypeDTOMapper;
        this.aircraftRepository = aircraftRepository;
        this.toolRepository = toolRepository;
        this.toolKitRepository = toolKitRepository;
    }

    public List<EngineTypeResponseDTO> findAllEngineTypes() {
        return engineTypeDTOMapper.mapToDto(engineTypeRepository.findAll());
    }

    public EngineTypeResponseDTO findEngineTypeById(Long id) {
        EngineTypeEntity engineTypeEntity = getEngineTypeEntity(id);
        return engineTypeDTOMapper.mapToDto(engineTypeEntity);
    }

    public EngineTypeResponseDTO createEngineType(EngineTypeRequestDTO model) {
        EngineTypeEntity engineTypeEntity = engineTypeDTOMapper.mapToEntity(model);
        engineTypeEntity = engineTypeRepository.save(engineTypeEntity);
        return engineTypeDTOMapper.mapToDto(engineTypeEntity);
    }

    public EngineTypeResponseDTO updateEngineType(Long id, EngineTypeRequestDTO requestDto) {
        EngineTypeEntity existingEntity = getEngineTypeEntity(id);

        existingEntity.setManufacturer(requestDto.getManufacturer());
        existingEntity.setMainType(requestDto.getMainType());
        existingEntity.setSubType(requestDto.getSubType());

        existingEntity = engineTypeRepository.save(existingEntity);
        return engineTypeDTOMapper.mapToDto(existingEntity);
    }

    public void deleteEngineType(Long id) {
        if (aircraftRepository.existsByAircraftTypeId(id)) {
            throw new ForeignKeyViolationException("Engine type is still referenced by an aircraft.");
        }
        if (toolRepository.existsByEngineTypeId(id)) {
            throw new ForeignKeyViolationException("Engine type is still referenced by a tool.");
        }
        if (toolKitRepository.existsByEngineTypeId(id)) {
            throw new ForeignKeyViolationException("Engine type is still referenced by a tool kit.");
        }
        EngineTypeEntity engineType = getEngineTypeEntity(id);
        engineTypeRepository.deleteById(id);
    }

    //Generic FindById Helper
    private EngineTypeEntity getEngineTypeEntity(Long id) {
        EngineTypeEntity engineTypeEntity = engineTypeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Engine type " + id + " not found"));
        return engineTypeEntity;
    }
}