package nl.novi.deepwrench42.services;


import nl.novi.deepwrench42.dtos.aircraftType.AircraftTypeRequestDTO;
import nl.novi.deepwrench42.dtos.aircraftType.AircraftTypeResponseDTO;
import nl.novi.deepwrench42.entities.AircraftTypeEntity;
import nl.novi.deepwrench42.mappers.AircraftTypeDTOMapper;
import nl.novi.deepwrench42.repository.AircraftTypeRepository;

import java.util.List;

public class AircraftTypeService {
    private final AircraftTypeRepository aircraftTypeRepository;
    private final AircraftTypeDTOMapper aircraftTypeDTOMapper;

    public AircraftTypeService(AircraftTypeRepository aircraftTypeRepository, AircraftTypeDTOMapper aircraftTypeDTOMapper) {
        this.aircraftTypeRepository = aircraftTypeRepository;
        this.aircraftTypeDTOMapper = aircraftTypeDTOMapper;
    }

    public List<AircraftTypeResponseDTO> findAllUsers() {
        return aircraftTypeDTOMapper.mapToDto(aircraftTypeRepository.findAll());
    }

    public AircraftTypeResponseDTO findUserById(Long id)  {
        AircraftTypeEntity aircraftTypeEntity = getAircraftTypeEntity(id);
        return aircraftTypeDTOMapper.mapToDto(aircraftTypeEntity);
    }


    public AircraftTypeResponseDTO createUser(AircraftTypeRequestDTO model) {
        AircraftTypeEntity aircraftTypeEntity = aircraftTypeDTOMapper.mapToEntity(model);
        aircraftTypeEntity = aircraftTypeRepository.save(aircraftTypeEntity);
        return aircraftTypeDTOMapper.mapToDto(aircraftTypeEntity);
    }

    public AircraftTypeResponseDTO updateUser(Long id, AircraftTypeRequestDTO requestDto)  {
        AircraftTypeEntity existingEntity = getAircraftTypeEntity(id);

        existingEntity.setManufacturer(requestDto.getManufacturer());
        existingEntity.setMainType(requestDto.getMainType());
        existingEntity.setSubType(requestDto.getSubType());

        existingEntity = aircraftTypeRepository.save(existingEntity);
        return aircraftTypeDTOMapper.mapToDto(existingEntity);
    }

    public void deleteUser(Long id) {
        AircraftTypeEntity aircraftType = getAircraftTypeEntity(id);
        aircraftTypeRepository.deleteById(id);
    }

    // Generic ID getter
    private AircraftTypeEntity getAircraftTypeEntity(Long id) {
        AircraftTypeEntity aircraftTypeEntity = aircraftTypeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Aircraft type " + id +" not found"));
        return aircraftTypeEntity;
    }
}