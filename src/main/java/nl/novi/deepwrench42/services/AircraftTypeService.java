package nl.novi.deepwrench42.services;


import nl.novi.deepwrench42.dtos.aircraftType.AircraftTypeRequestDTO;
import nl.novi.deepwrench42.dtos.aircraftType.AircraftTypeResponseDTO;
import nl.novi.deepwrench42.entities.AircraftTypeEntity;
import nl.novi.deepwrench42.exceptions.ForeignKeyViolationException;
import nl.novi.deepwrench42.exceptions.RecordNotFoundException;
import nl.novi.deepwrench42.mappers.AircraftTypeDTOMapper;
import nl.novi.deepwrench42.repository.AircraftRepository;
import nl.novi.deepwrench42.repository.AircraftTypeRepository;
import nl.novi.deepwrench42.repository.ToolKitRepository;
import nl.novi.deepwrench42.repository.ToolRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AircraftTypeService {
    private final AircraftTypeRepository aircraftTypeRepository;
    private final AircraftTypeDTOMapper aircraftTypeDTOMapper;
    private final AircraftRepository aircraftRepository;
    private final ToolRepository toolRepository;
    private final ToolKitRepository toolKitRepository;

    public AircraftTypeService(AircraftTypeRepository aircraftTypeRepository, AircraftTypeDTOMapper aircraftTypeDTOMapper, AircraftRepository aircraftRepository, ToolRepository toolRepository, ToolKitRepository toolKitRepository) {
        this.aircraftTypeRepository = aircraftTypeRepository;
        this.aircraftTypeDTOMapper = aircraftTypeDTOMapper;
        this.aircraftRepository = aircraftRepository;
        this.toolRepository = toolRepository;
        this.toolKitRepository = toolKitRepository;
    }

    public List<AircraftTypeResponseDTO> findAllAircraftTypes() {
        return aircraftTypeDTOMapper.mapToDto(aircraftTypeRepository.findAll());
    }

    public AircraftTypeResponseDTO findAircraftTypeById(Long id) {
        AircraftTypeEntity aircraftTypeEntity = getAircraftTypeEntity(id);
        return aircraftTypeDTOMapper.mapToDto(aircraftTypeEntity);
    }


    public AircraftTypeResponseDTO createAircraftType(AircraftTypeRequestDTO model) {
        AircraftTypeEntity aircraftTypeEntity = aircraftTypeDTOMapper.mapToEntity(model);
        aircraftTypeEntity = aircraftTypeRepository.save(aircraftTypeEntity);
        return aircraftTypeDTOMapper.mapToDto(aircraftTypeEntity);
    }

    public AircraftTypeResponseDTO updateAircraftType(Long id, AircraftTypeRequestDTO requestDto) {
        AircraftTypeEntity existingEntity = getAircraftTypeEntity(id);

        existingEntity.setManufacturer(requestDto.getManufacturer());
        existingEntity.setMainType(requestDto.getMainType());
        existingEntity.setSubType(requestDto.getSubType());

        existingEntity = aircraftTypeRepository.save(existingEntity);
        return aircraftTypeDTOMapper.mapToDto(existingEntity);
    }

    public void deleteAircraftType(Long id) {
        if (aircraftRepository.existsByAircraftTypeId(id)) {
            throw new ForeignKeyViolationException("Aircraft type is still referenced by an aircraft.");
        }
        if (toolRepository.existsByAircraftTypeId(id)) {
            throw new ForeignKeyViolationException("Aircraft type is still referenced by a tool.");
        }
        if (toolKitRepository.existsByAircraftTypeId(id)) {
            throw new ForeignKeyViolationException("Aircraft type is still referenced by a tool kit.");
        }
        AircraftTypeEntity aircraftType = getAircraftTypeEntity(id);
        aircraftTypeRepository.deleteById(id);
    }

    //Generic FIndById Helper
    private AircraftTypeEntity getAircraftTypeEntity(Long id) {
        AircraftTypeEntity aircraftTypeEntity = aircraftTypeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Aircraft type " + id + " not found"));
        return aircraftTypeEntity;
    }
}