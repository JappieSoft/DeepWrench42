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


    public AircraftTypeResponseDTO createUser(AircraftTypeRequestDTO aircraftTypeModel) {
        AircraftTypeEntity aircraftTypeEntity = aircraftTypeDTOMapper.mapToEntity(aircraftTypeModel);
        aircraftTypeEntity = aircraftTypeRepository.save(aircraftTypeEntity);
        return aircraftTypeDTOMapper.mapToDto(aircraftTypeEntity);
    }

    public AircraftTypeResponseDTO updateUser(Long id, AircraftTypeRequestDTO aircraftTypeModel)  {
        AircraftTypeEntity existingAircraftTypeEntity = getAircraftTypeEntity(id);

        existingAircraftTypeEntity.setManufacturer(aircraftTypeModel.getManufacturer());
        existingAircraftTypeEntity.setMainType(aircraftTypeModel.getMainType());
        existingAircraftTypeEntity.setSubType(aircraftTypeModel.getSubType());

        existingAircraftTypeEntity = aircraftTypeRepository.save(existingAircraftTypeEntity);
        return aircraftTypeDTOMapper.mapToDto(existingAircraftTypeEntity);
    }

    public void deleteUser(Long id) {
        AircraftTypeEntity user = getAircraftTypeEntity(id);
        aircraftTypeRepository.deleteById(id);
    }

    // Generic ID getter
    private AircraftTypeEntity getAircraftTypeEntity(Long id) {
        AircraftTypeEntity aircraftTypeEntity = aircraftTypeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("User " + id +" not found"));
        return aircraftTypeEntity;
    }
}