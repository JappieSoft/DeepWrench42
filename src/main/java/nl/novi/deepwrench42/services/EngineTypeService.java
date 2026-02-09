package nl.novi.deepwrench42.services;


import nl.novi.deepwrench42.dtos.engineType.EngineTypeRequestDTO;
import nl.novi.deepwrench42.dtos.engineType.EngineTypeResponseDTO;
import nl.novi.deepwrench42.entities.EngineTypeEntity;
import nl.novi.deepwrench42.mappers.EngineTypeDTOMapper;
import nl.novi.deepwrench42.repository.EngineTypeRepository;

import java.util.List;

public class EngineTypeService {
    private final EngineTypeRepository engineTypeRepository;
    private final EngineTypeDTOMapper engineTypeDTOMapper;

    public EngineTypeService(EngineTypeRepository engineTypeRepository, EngineTypeDTOMapper engineTypeDTOMapper) {
        this.engineTypeRepository = engineTypeRepository;
        this.engineTypeDTOMapper = engineTypeDTOMapper;
    }

    public List<EngineTypeResponseDTO> findAllUsers() {
        return engineTypeDTOMapper.mapToDto(engineTypeRepository.findAll());
    }

    public EngineTypeResponseDTO findUserById(Long id)  {
        EngineTypeEntity engineTypeEntity = getEngineTypeEntity(id);
        return engineTypeDTOMapper.mapToDto(engineTypeEntity);
    }


    public EngineTypeResponseDTO createUser(EngineTypeRequestDTO model) {
        EngineTypeEntity engineTypeEntity = engineTypeDTOMapper.mapToEntity(model);
        engineTypeEntity = engineTypeRepository.save(engineTypeEntity);
        return engineTypeDTOMapper.mapToDto(engineTypeEntity);
    }

    public EngineTypeResponseDTO updateUser(Long id, EngineTypeRequestDTO requestDto)  {
        EngineTypeEntity existingEntity = getEngineTypeEntity(id);

        existingEntity.setManufacturer(requestDto.getManufacturer());
        existingEntity.setMainType(requestDto.getMainType());
        existingEntity.setSubType(requestDto.getSubType());

        existingEntity = engineTypeRepository.save(existingEntity);
        return engineTypeDTOMapper.mapToDto(existingEntity);
    }

    public void deleteUser(Long id) {
        EngineTypeEntity user = getEngineTypeEntity(id);
        engineTypeRepository.deleteById(id);
    }

    // Generic ID getter
    private EngineTypeEntity getEngineTypeEntity(Long id) {
        EngineTypeEntity engineTypeEntity = engineTypeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Engine type " + id +" not found"));
        return engineTypeEntity;
    }
}