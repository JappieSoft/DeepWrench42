package nl.novi.deepwrench42.services;

import nl.novi.deepwrench42.dtos.user.UserRequestDTO;
import nl.novi.deepwrench42.dtos.user.UserResponseDTO;
import nl.novi.deepwrench42.entities.UserEntity;
import nl.novi.deepwrench42.entities.UserRole;
import nl.novi.deepwrench42.exceptions.DuplicateFieldException;
import nl.novi.deepwrench42.exceptions.RecordNotFoundException;
import nl.novi.deepwrench42.mappers.UserDTOMapper;
import nl.novi.deepwrench42.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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

    public UserResponseDTO findUserByKcid(Authentication authentication) {
        String kcid = authentication.getName();
        UserEntity userEntity = getKcidUserEntity(kcid);
        return userDtoMapper.mapToDto(userEntity);
    }

    public UserResponseDTO findUserById(Long id) {
        UserEntity userEntity = getUserEntity(id);
        return userDtoMapper.mapToDto(userEntity);
    }

    public UserResponseDTO createUser(UserRequestDTO userModel, Authentication authentication) {
        if (userRepository.existsByEmployeeId(userModel.getEmployeeId())) {
            throw new DuplicateFieldException("Employee ID already exists");
        }
        if (userRepository.existsBySchipholId(userModel.getSchipholId())) {
            throw new DuplicateFieldException("Schiphol ID already exists");
        }
        if (userRepository.existsByEmail(userModel.getEmail())) {
            throw new DuplicateFieldException("Email already exists");
        }
        if (userRepository.existsByKcid(authentication.getName())) {
            throw new DuplicateFieldException("User already exists");
        }

        Set<String> authorities = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        String role;
        if (authorities.contains("ADMIN")) {
            role = "ADMIN";
        } else if (authorities.contains("LEAD")) {
            role = "LEAD";
        } else {
            role = "USER";
        }

        UserRequestDTO extendInput = new UserRequestDTO();
        extendInput.setEmployeeId(userModel.getEmployeeId());
        extendInput.setKcid(authentication.getName());
        extendInput.setFirstName(userModel.getFirstName());
        extendInput.setLastName(userModel.getLastName());
        extendInput.setSchipholId(userModel.getSchipholId());
        extendInput.setEmail(userModel.getEmail());
        extendInput.setRole(UserRole.valueOf(role));

        UserEntity userEntity = userDtoMapper.mapToEntity(extendInput);
        userEntity = userRepository.save(userEntity);
        return userDtoMapper.mapToDto(userEntity);
    }

    public UserResponseDTO updateUser(UserRequestDTO requestDto, Authentication authentication) {
        UserEntity existingEntity = getKcidUserEntity(authentication.getName());

        Set<String> authorities = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        String role;
        if (authorities.contains("ADMIN")) {
            role = "ADMIN";
        } else if (authorities.contains("LEAD")) {
            role = "LEAD";
        } else {
            role = "USER";
        }

        existingEntity.setEmployeeId(requestDto.getEmployeeId());
        existingEntity.setSchipholId(requestDto.getSchipholId());
        existingEntity.setEmail(requestDto.getEmail());
        existingEntity.setFirstName(requestDto.getFirstName());
        existingEntity.setLastName(requestDto.getLastName());
        existingEntity.setRole(UserRole.valueOf(role));

        existingEntity = userRepository.save(existingEntity);
        return userDtoMapper.mapToDto(existingEntity);
    }

    public UserResponseDTO patchUser(Long id, UserRequestDTO requestDto) {
        UserEntity existingEntity = getUserEntity(id);

        existingEntity.setKcid(requestDto.getKcid());
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

    //Generic FIndById Helper
    private UserEntity getUserEntity(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("User " + id + " not found"));
        return userEntity;
    }

    private UserEntity getKcidUserEntity(String kcid) {
        if (userRepository.existsByKcid(kcid)) {
            return userRepository.findByKcid(kcid);
        } else {
            throw new RecordNotFoundException("User Profile not found, please create User Profile.");
        }
    }
}

