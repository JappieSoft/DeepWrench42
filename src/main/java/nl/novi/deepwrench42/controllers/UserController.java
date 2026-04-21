package nl.novi.deepwrench42.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import nl.novi.deepwrench42.dtos.user.UserRequestDTO;
import nl.novi.deepwrench42.dtos.user.UserResponseDTO;
import nl.novi.deepwrench42.helpers.UrlHelper;
import nl.novi.deepwrench42.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user")
@Tag(name = "User Management")
public class UserController {

    private final UserService userService;
    private final UrlHelper urlHelper;

    public UserController(UserService userService, UrlHelper urlHelper) {
        this.userService = userService;
        this.urlHelper = urlHelper;
    }
    @Operation(
                description = "Get all users details if signed in as admin, otherwise get current user details",
                summary = "Get the details of current user or all users"
    )
    @GetMapping()
    public ResponseEntity<List<UserResponseDTO>> getUsers(Authentication authentication) {
        Set<String> authorities = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (authorities.contains("ADMIN")) {
            List<UserResponseDTO> users = userService.findAllUsers();
            return ResponseEntity.ok(users);
        } else if (authorities.contains("USER")) {
            UserResponseDTO user = userService.findUserByKcid(authentication);
            return ResponseEntity.ok(List.of(user));
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    @Operation(
            description = "Get user details of a specific user id, admin only",
            summary = "Get the details of a specific user id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        UserResponseDTO user = userService.findUserById(id);
        return new ResponseEntity<UserResponseDTO>(user, HttpStatus.OK);
    }

    @Operation(
            description = "Post user details of current user",
            summary = "Post current user details"
    )
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO userInput, Authentication authentication) {
        UserResponseDTO newUser = userService.createUser(userInput, authentication);
        return ResponseEntity.created(urlHelper.getCurrentUrlWithId(newUser.getId())).body(newUser);
    }

    @Operation(
            description = "Update user details of current user",
            summary = "Update current user details"
    )
    @PutMapping
    public ResponseEntity<UserResponseDTO> updateUser(@Valid @RequestBody UserRequestDTO userInput, Authentication authentication) {
        UserResponseDTO updatedUser = userService.updateUser(userInput, authentication);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @Operation(
            description = "Patch user details of specific user id, admin only",
            summary = "Patch user details of specific user id"
    )
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDTO> patchUser(@PathVariable Long id, @Valid @RequestBody UserRequestDTO userInput) {
        UserResponseDTO updatedUser = userService.patchUser(id, userInput);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @Operation(
            description = "Delete a specific user id, admin only",
            summary = "Delete a specific user id"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
