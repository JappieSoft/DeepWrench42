package nl.novi.deepwrench42.services;

import nl.novi.deepwrench42.dtos.user.UserRequestDTO;
import nl.novi.deepwrench42.dtos.user.UserResponseDTO;
import nl.novi.deepwrench42.entities.UserEntity;
import nl.novi.deepwrench42.mappers.UserDTOMapper;
import nl.novi.deepwrench42.repository.UserRepository;

import java.util.List;

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

    public UserResponseDTO updateUser(Long id, UserRequestDTO userModel)  {
        UserEntity existingUserEntity = getUserEntity(id);

        existingUserEntity.setEmployeeId(userModel.getEmployeeId());
        existingUserEntity.setSchipholId(userModel.getSchipholId());
        existingUserEntity.setEmail(userModel.getEmail());
        existingUserEntity.setFirstName(userModel.getFirstName());
        existingUserEntity.setLastName(userModel.getLastName());
        existingUserEntity.setRole(userModel.getRole());

        existingUserEntity = userRepository.save(existingUserEntity);
        return userDtoMapper.mapToDto(existingUserEntity);
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

