package nl.novi.deepwrench42.mappers;

import nl.novi.deepwrench42.dtos.equipment.EquipmentRequestDTO;
import nl.novi.deepwrench42.dtos.equipment.EquipmentResponseDTO;
import nl.novi.deepwrench42.entities.EquipmentEntity;
import nl.novi.deepwrench42.entities.EquipmentStatus;
import org.springframework.stereotype.Component;

@Component
public class EquipmentDTOMapper {

    private final StorageLocationDTOMapper storageLocationDTOMapper;
    private final UserDTOMapper userDTOMapper;

    public EquipmentDTOMapper(StorageLocationDTOMapper storageLocationDTOMapper, UserDTOMapper userDTOMapper) {
        this.storageLocationDTOMapper = storageLocationDTOMapper;
        this.userDTOMapper = userDTOMapper;
    }

    protected EquipmentResponseDTO equipmentMapToDto(EquipmentEntity model, EquipmentResponseDTO result) {
        result.setId(model.getId());
        result.setEquipmentType(model.getEquipmentType());
        result.setItemId(model.getItemId());
        result.setName(model.getName());
        result.setPictureFileName(model.getPictureFileName());
        result.setStorageLocation(storageLocationDTOMapper.mapToDto(model.getStorageLocation()));
        result.setStatus(model.getStatus());
        result.setCheckedOutBy(userDTOMapper.mapToDto(model.getCheckedOutBy()));
        result.setCheckedOutDate(model.getCheckedOutDate());
        result.setHasInspection(model.getHasInspection());
        result.setComments(model.getComments());

        return result;
    }

    protected void mapToEquipmentEntity(EquipmentRequestDTO requestDTO, EquipmentEntity entity) {
        entity.setEquipmentType(requestDTO.getEquipmentType());
        entity.setItemId(requestDTO.getItemId());
        entity.setName(requestDTO.getName());
        entity.setPictureFileName(requestDTO.getPictureFileName());
        entity.setStatus(EquipmentStatus.valueOf(requestDTO.getStatus().toUpperCase()));
        entity.setCheckedOutDate(requestDTO.getCheckedOutDate());
        entity.setHasInspection(requestDTO.getHasInspection());
        entity.setComments(requestDTO.getComments());
    }
}
