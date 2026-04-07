package nl.novi.deepwrench42.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.novi.deepwrench42.dtos.user.UserRequestDTO;
import nl.novi.deepwrench42.dtos.user.UserResponseDTO;
import nl.novi.deepwrench42.entities.UserEntity;
import nl.novi.deepwrench42.entities.UserRole;
import nl.novi.deepwrench42.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest()
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void getAllUsers_shouldReturnListOfUsers() throws Exception {
        UserEntity userOne = new UserEntity();
        userOne.setEmployeeId("No_One");
        userOne.setSchipholId("123456");
        userOne.setEmail("apple@tree.org");
        userOne.setFirstName("Charles");
        userOne.setLastName("Darwin");
        userOne.setRole(UserRole.valueOf("USER"));
        userRepository.save(userOne);

        UserEntity userTwo = new UserEntity();
        userTwo.setEmployeeId("No_Two");
        userTwo.setSchipholId("654321");
        userTwo.setEmail("gravity@sun.com");
        userTwo.setFirstName("Isaac");
        userTwo.setLastName("Newton");
        userTwo.setRole(UserRole.valueOf("LEAD"));
        userRepository.save(userTwo);

        mockMvc.perform(get("/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))

                .andExpect(jsonPath("$[0].employeeId").value("No_One"))
                .andExpect(jsonPath("$[0].schipholId").value("123456"))
                .andExpect(jsonPath("$[0].email").value("apple@tree.org"))
                .andExpect(jsonPath("$[0].firstName").value("Charles"))
                .andExpect(jsonPath("$[0].lastName").value("Darwin"))
                .andExpect(jsonPath("$[0].role").value(containsString("USER")))

                .andExpect(jsonPath("$[1].employeeId").value("No_Two"))
                .andExpect(jsonPath("$[1].schipholId").value("654321"))
                .andExpect(jsonPath("$[1].email").value("gravity@sun.com"))
                .andExpect(jsonPath("$[1].firstName").value("Isaac"))
                .andExpect(jsonPath("$[1].lastName").value("Newton"))
                .andExpect(jsonPath("$[1].role").value(containsString("LEAD")));
    }

    @Test
    void getUserById_shouldReturnUser_whenUserExists() throws Exception {
        UserEntity userThree = new UserEntity();
        userThree.setEmployeeId("NoThree");
        userThree.setSchipholId("78910");
        userThree.setEmail("dark@matter.nl");
        userThree.setFirstName("Stephen");
        userThree.setLastName("Hawking");
        userThree.setRole(UserRole.valueOf("ADMIN"));
        Long storageId = userRepository.save(userThree).getId();

        mockMvc.perform(get("/user/{id}", storageId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId").value("NoThree"))
                .andExpect(jsonPath("$.schipholId").value("78910"))
                .andExpect(jsonPath("$.email").value("dark@matter.nl"))
                .andExpect(jsonPath("$.firstName").value("Stephen"))
                .andExpect(jsonPath("$.lastName").value("Hawking"))
                .andExpect(jsonPath("$.role").value(containsString("ADMIN")));
    }

    @Test
    void getUserById_shouldReturnNotFound_whenUserDoesNotExist() throws Exception {
        mockMvc.perform(get("/user/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createUser_shouldCreateAndReturnUser() throws Exception {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setEmployeeId("CRT_USR");
        userRequestDTO.setSchipholId("112233");
        userRequestDTO.setEmail("email@google.com");
        userRequestDTO.setFirstName("Search");
        userRequestDTO.setLastName("Google");
        userRequestDTO.setRole(UserRole.valueOf("USER"));

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setEmployeeId("CRT_USR");
        userResponseDTO.setSchipholId("112233");
        userResponseDTO.setEmail("email@google.com");
        userResponseDTO.setFirstName("Search");
        userResponseDTO.setLastName("Google");
        userResponseDTO.setRole(UserRole.valueOf("USER"));

        var response = mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.employeeId").value("CRT_USR"))
                .andExpect(jsonPath("$.schipholId").value("112233"))
                .andExpect(jsonPath("$.email").value("email@google.com"))
                .andExpect(jsonPath("$.firstName").value("Search"))
                .andExpect(jsonPath("$.lastName").value("Google"))
                .andExpect(jsonPath("$.role").value(containsString("USER")))
                .andReturn();

        UserResponseDTO userResult = objectMapper.readValue(response.getResponse().getContentAsString(), UserResponseDTO.class);

        Assertions.assertEquals(userResponseDTO.getEmployeeId(), userResult.getEmployeeId());
        Assertions.assertEquals(userResponseDTO.getSchipholId(), userResult.getSchipholId());
        Assertions.assertEquals(userResponseDTO.getEmail(),      userResult.getEmail());
        Assertions.assertEquals(userResponseDTO.getFirstName(),  userResult.getFirstName());
        Assertions.assertEquals(userResponseDTO.getLastName(),   userResult.getLastName());
        Assertions.assertEquals(userResponseDTO.getRole(),       userResult.getRole());
    }

    @Test
    void createUser_shouldReturnEmployeeIdAlreadyExists_whenEmployeeIdAlreadyExists() throws Exception {
        UserEntity user = new UserEntity();
        user.setEmployeeId("CRTERR1");
        user.setSchipholId("111111");
        user.setEmail("email@error.nl");
        user.setFirstName("employee");
        user.setLastName("ID");
        user.setRole(UserRole.valueOf("USER"));
        userRepository.save(user);

        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setEmployeeId("CRTERR1");
        userRequestDTO.setSchipholId("999999");
        userRequestDTO.setEmail("error@number1.com");
        userRequestDTO.setFirstName("employee");
        userRequestDTO.setLastName("ID");
        userRequestDTO.setRole(UserRole.valueOf("USER"));

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isConflict());
    }

    @Test
    void createUser_shouldReturnSchipholIdAlreadyExists_whenSchipholIdAlreadyExists() throws Exception {
        UserEntity user = new UserEntity();
        user.setEmployeeId("222222");
        user.setSchipholId("CRTERR2");
        user.setEmail("email@error.org");
        user.setFirstName("employee");
        user.setLastName("ID");
        user.setRole(UserRole.valueOf("USER"));
        userRepository.save(user);

        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setEmployeeId("000000");
        userRequestDTO.setSchipholId("CRTERR2");
        userRequestDTO.setEmail("error@number2.com");
        userRequestDTO.setFirstName("employee");
        userRequestDTO.setLastName("ID");
        userRequestDTO.setRole(UserRole.valueOf("USER"));

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isConflict());
    }

    @Test
    void createUser_shouldReturnEmailAlreadyExists_whenEmailAlreadyExists() throws Exception {
        UserEntity user = new UserEntity();
        user.setEmployeeId("CRTERR3");
        user.setSchipholId("333333");
        user.setEmail("creat@error.com");
        user.setFirstName("employee");
        user.setLastName("ID");
        user.setRole(UserRole.valueOf("USER"));
        userRepository.save(user);

        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setEmployeeId("ERROR3");
        userRequestDTO.setSchipholId("444444");
        userRequestDTO.setEmail("creat@error.com");
        userRequestDTO.setFirstName("employee");
        userRequestDTO.setLastName("ID");
        userRequestDTO.setRole(UserRole.valueOf("USER"));

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isConflict());
    }

    @Test
    void updateUser_shouldUpdateAndReturnUser_whenUserExists() throws Exception {
        UserEntity user = new UserEntity();
        user.setEmployeeId("CRT_DTO");
        user.setSchipholId("112233");
        user.setEmail("email@google.com");
        user.setFirstName("Search");
        user.setLastName("Google");
        user.setRole(UserRole.valueOf("USER"));
        Long userId = userRepository.save(user).getId();

        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setEmployeeId("UPD_DTO");
        userRequestDTO.setSchipholId("334455");
        userRequestDTO.setEmail("email@apple.com");
        userRequestDTO.setFirstName("Apple");
        userRequestDTO.setLastName("Took Over");
        userRequestDTO.setRole(UserRole.valueOf("LEAD"));

        mockMvc.perform(put("/user/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId").value("UPD_DTO"))
                .andExpect(jsonPath("$.schipholId").value("334455"))
                .andExpect(jsonPath("$.email").value("email@apple.com"))
                .andExpect(jsonPath("$.firstName").value("Apple"))
                .andExpect(jsonPath("$.lastName").value("Took Over"))
                .andExpect(jsonPath("$.role").value(containsString("LEAD")));
    }

    @Test
    void updateUser_shouldReturnNotFound_whenUserDoesNotExist() throws Exception {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setEmployeeId("NOT_UPD");
        userRequestDTO.setSchipholId("667788");
        userRequestDTO.setEmail("email@none.com");
        userRequestDTO.setFirstName("No");
        userRequestDTO.setLastName("One");
        userRequestDTO.setRole(UserRole.valueOf("ADMIN"));

        mockMvc.perform(put("/user/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteUser_shouldDeleteUser_whenUserExists() throws Exception {
        UserEntity user = new UserEntity();
        user.setEmployeeId("DLT_DTO");
        user.setSchipholId("666777");
        user.setEmail("delete@email.nl");
        user.setFirstName("Lost");
        user.setLastName("Somewhere");
        user.setRole(UserRole.valueOf("USER"));
        Long userId = userRepository.save(user).getId();

        mockMvc.perform(delete("/user/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/user/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteUser_shouldReturnNotFound_whenUserDoesNotExist() throws Exception {
        mockMvc.perform(delete("/user/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
