package nl.novi.deepwrench42.services;

import nl.novi.deepwrench42.dtos.user.UserRequestDTO;
import nl.novi.deepwrench42.dtos.user.UserResponseDTO;
import nl.novi.deepwrench42.entities.UserEntity;
import nl.novi.deepwrench42.exceptions.RecordNotFoundException;
import nl.novi.deepwrench42.mappers.UserDTOMapper;
import nl.novi.deepwrench42.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserDTOMapper userDtoMapper;

    public UserService(UserRepository userRepository, UserDTOMapper userDtoMapper) {
        this.userRepository = userRepository;
        this.userDtoMapper = userDtoMapper;
    }

    public List<UserResponseDTO> findAllUsers() {
        return userDtoMapper.mapToDto(userRepository.findAll());
    }

    public UserResponseDTO findUserById(Long id)  {
        UserEntity userEntity = getUserEntity(id);
        return userDtoMapper.mapToDto(userEntity);
    }


    public UserResponseDTO createUser(UserRequestDTO userModel) {
        UserEntity userEntity = userDtoMapper.mapToEntity(userModel);
        userEntity = userRepository.save(userEntity);
        return userDtoMapper.mapToDto(userEntity);
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO requestDto)  {
        UserEntity existingEntity = getUserEntity(id);

        existingEntity.setEmployeeId(requestDto.getEmployeeId());
        existingEntity.setSchipholId(requestDto.getSchipholId());
        existingEntity.setEmail(requestDto.getEmail());
        existingEntity.setFirstName(requestDto.getFirstName());
        existingEntity.setLastName(requestDto.getLastName());
        existingEntity.setRole(requestDto.getRole());

        existingEntity = userRepository.save(existingEntity);
        return userDtoMapper.mapToDto(existingEntity);
    }

    public void deleteUser(Long id) {
        UserEntity user = getUserEntity(id);
        userRepository.deleteById(id);
    }

    // Generic ID getter
    private UserEntity getUserEntity(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("User " + id +" not found"));
        return userEntity;
    }
}

