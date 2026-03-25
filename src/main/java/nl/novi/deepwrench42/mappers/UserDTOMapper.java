package nl.novi.deepwrench42.mappers;

import nl.novi.deepwrench42.dtos.user.UserRequestDTO;
import nl.novi.deepwrench42.dtos.user.UserResponseDTO;
import nl.novi.deepwrench42.entities.UserEntity;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class UserDTOMapper implements DTOMapper<UserResponseDTO, UserRequestDTO, UserEntity>{

    @Override
    public UserResponseDTO mapToDto(UserEntity model) {
        if (model == null) return null;

        var result = new UserResponseDTO();
        result.setId(model.getId());
        result.setEmployeeId(model.getEmployeeId());
        result.setSchipholId(model.getSchipholId());
        result.setEmail(model.getEmail());
        result.setFirstName(model.getFirstName());
        result.setLastName(model.getLastName());
        result.setRole(model.getRole());
        return result;
    }

    @Override
    public List<UserResponseDTO> mapToDto(List<UserEntity> models) {
        if (models == null || models.isEmpty()) return List.of();

        return models.stream()
                .map(this::mapToDto)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public UserEntity mapToEntity(UserRequestDTO requestDTO) {
        if (requestDTO == null) return null;

        var model = new UserEntity();
        model.setEmployeeId(requestDTO.getEmployeeId());
        model.setSchipholId(requestDTO.getSchipholId());
        model.setEmail(requestDTO.getEmail());
        model.setFirstName(requestDTO.getFirstName());
        model.setLastName(requestDTO.getLastName());
        model.setRole(requestDTO.getRole());
        return model;
    }
}
