package nl.novi.deepwrench42.services;

import nl.novi.deepwrench42.dtos.storageLocation.StorageLocationRequestDTO;
import nl.novi.deepwrench42.dtos.storageLocation.StorageLocationResponseDTO;
import nl.novi.deepwrench42.entities.StorageLocationEntity;
import nl.novi.deepwrench42.exceptions.RecordNotFoundException;
import nl.novi.deepwrench42.mappers.StorageLocationDTOMapper;
import nl.novi.deepwrench42.repository.StorageLocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StorageLocationService {
    private final StorageLocationRepository storageLocationRepository;
    private final StorageLocationDTOMapper storageLocationDTOMapper;

    public StorageLocationService(StorageLocationRepository storageLocationRepository, StorageLocationDTOMapper storageLocationDTOMapper) {
        this.storageLocationRepository = storageLocationRepository;
        this.storageLocationDTOMapper = storageLocationDTOMapper;
    }

    public List<StorageLocationResponseDTO> findAllStorageLocations() {
        return storageLocationDTOMapper.mapToDto(storageLocationRepository.findAll());
    }

    public StorageLocationResponseDTO findStorageLocationById(Long id)  {
        StorageLocationEntity storageLocationEntity = getStorageLocationEntity(id);
        return storageLocationDTOMapper.mapToDto(storageLocationEntity);
    }


    public StorageLocationResponseDTO createStorageLocation(StorageLocationRequestDTO model) {
        StorageLocationEntity storageLocationEntity = storageLocationDTOMapper.mapToEntity(model);
        storageLocationEntity = storageLocationRepository.save(storageLocationEntity);
        return storageLocationDTOMapper.mapToDto(storageLocationEntity);
    }

    public StorageLocationResponseDTO updateStorageLocation(Long id, StorageLocationRequestDTO requestDto)  {
        StorageLocationEntity existingEntity = getStorageLocationEntity(id);

        existingEntity.setLocation(requestDto.getLocation());
        existingEntity.setRack(requestDto.getRack());
        existingEntity.setShelf(requestDto.getShelf());
        existingEntity.setComments(requestDto.getComments());

        existingEntity = storageLocationRepository.save(existingEntity);
        return storageLocationDTOMapper.mapToDto(existingEntity);
    }

    public void deleteStorageLocation(Long id) {
        StorageLocationEntity storageLocation = getStorageLocationEntity(id);
        storageLocationRepository.deleteById(id);
    }

    // Generic ID getter
    private StorageLocationEntity getStorageLocationEntity(Long id) {
        StorageLocationEntity storageLocationEntity = storageLocationRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Storage location " + id +" not found"));
        return storageLocationEntity;
    }
}