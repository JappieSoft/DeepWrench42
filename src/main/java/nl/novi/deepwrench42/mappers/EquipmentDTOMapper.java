package nl.novi.deepwrench42.mappers;

import nl.novi.deepwrench42.dtos.equipment.EquipmentCheckInResponseDTO;
import nl.novi.deepwrench42.dtos.equipment.EquipmentCheckOutResponseDTO;
import nl.novi.deepwrench42.dtos.equipment.EquipmentRequestDTO;
import nl.novi.deepwrench42.dtos.equipment.EquipmentResponseDTO;
import nl.novi.deepwrench42.entities.EquipmentEntity;
import nl.novi.deepwrench42.entities.EquipmentStatus;
import nl.novi.deepwrench42.entities.ToolLogEntity;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class EquipmentDTOMapper{

    private final StorageLocationDTOMapper storageLocationDTOMapper;
    private final UserDTOMapper userDTOMapper;
    private final AircraftDTOMapper aircraftDTOMapper;

     public EquipmentDTOMapper(StorageLocationDTOMapper storageLocationDTOMapper, UserDTOMapper userDTOMapper, AircraftDTOMapper aircraftDTOMapper) {
         this.storageLocationDTOMapper = storageLocationDTOMapper;
         this.userDTOMapper = userDTOMapper;
         this.aircraftDTOMapper = aircraftDTOMapper;
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
