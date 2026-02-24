package nl.novi.deepwrench42.mappers;

import nl.novi.deepwrench42.dtos.equipment.EquipmentResponseDTO;
import nl.novi.deepwrench42.entities.EquipmentEntity;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class EquipmentDTOMapper{

    private final StorageLocationDTOMapper storageLocationDTOMapper;
    @Lazy
    private final InspectionDTOMapper inspectionDTOMapper;
    private final UserDTOMapper userDTOMapper;

     public EquipmentDTOMapper(StorageLocationDTOMapper storageLocationDTOMapper, InspectionDTOMapper inspectionDTOMapper, UserDTOMapper userDTOMapper) {this.storageLocationDTOMapper = storageLocationDTOMapper; this.inspectionDTOMapper = inspectionDTOMapper; this.userDTOMapper = userDTOMapper;}

        public EquipmentResponseDTO mapToDto(EquipmentEntity model) {
            if (model == null) return null;

            var result = new EquipmentResponseDTO();
            result.setId(model.getId());
            result.setEquipmentType(model.getEquipmentType());
            result.setItemId(model.getItemId());
            result.setName(model.getName());
            result.setPicture(model.getPicture());
            result.setStorageLocation(storageLocationDTOMapper.mapToDto(model.getStorageLocation()));
            result.setStatus(model.getStatus());
            result.setCheckedOutBy(userDTOMapper.mapToDto(model.getCheckedOutBy()));
            result.setCheckedOutDate(model.getCheckedOutDate());
            result.setHasInspection(model.getHasInspection());
            result.setComments(model.getComments());

            return result;
        }

        public List<EquipmentResponseDTO> mapToDto(List<EquipmentEntity> models) {
            if (models == null || models.isEmpty()) return List.of();

            return models.stream()
                    .map(this::mapToDto)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }

}
