package nl.novi.deepwrench42.controllers;

import jakarta.validation.Valid;
import nl.novi.deepwrench42.dtos.user.UserRequestDTO;
import nl.novi.deepwrench42.dtos.user.UserResponseDTO;
import nl.novi.deepwrench42.helpers.UrlHelper;
import nl.novi.deepwrench42.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UrlHelper urlHelper;

    public UserController(UserService userService, UrlHelper urlHelper) {
        this.userService = userService;
        this.urlHelper = urlHelper;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        UserResponseDTO user = userService.findUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<UserResponseDTO>(user, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO userInput) {
        UserResponseDTO newUser = userService.createUser(userInput);
        return ResponseEntity.created(urlHelper.getCurrentUrlWithId(newUser.getId())).body(newUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestDTO userInput) {
        UserResponseDTO updatedUser = userService.updateUser(id, userInput);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
