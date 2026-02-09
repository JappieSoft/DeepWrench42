package nl.novi.deepwrench42.mappers;

import nl.novi.deepwrench42.dtos.storageLocation.StorageLocationRequestDTO;
import nl.novi.deepwrench42.dtos.storageLocation.StorageLocationResponseDTO;
import nl.novi.deepwrench42.entities.StorageLocationEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class StorageLocationDTOMapper implements DTOMapper<StorageLocationResponseDTO, StorageLocationRequestDTO, StorageLocationEntity>{

    @Override
    public StorageLocationResponseDTO mapToDto(StorageLocationEntity model) {
        if (model == null) return null;

        var result = new StorageLocationResponseDTO();
        result.setId(model.getId());
        result.setLocation(model.getLocation());
        result.setRack(model.getRack());
        result.setShelf(model.getShelf());
        result.setComments(model.getComments());
        return result;
    }

    @Override
    public List<StorageLocationResponseDTO> mapToDto(List<StorageLocationEntity> models) {
        if (models == null || models.isEmpty()) return List.of();

        return models.stream()
                .map(this::mapToDto)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public StorageLocationEntity mapToEntity(StorageLocationRequestDTO requestDTO) {
        if (requestDTO == null) return null;

        var model = new StorageLocationEntity();
        model.setLocation(requestDTO.getLocation());
        model.setRack(requestDTO.getRack());
        model.setShelf(requestDTO.getShelf());
        model.setComments(requestDTO.getComments());
        return model;
    }
}
