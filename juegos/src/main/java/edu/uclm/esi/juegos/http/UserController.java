package edu.uclm.esi.juegos.http;

import edu.uclm.esi.juegos.services.UserService;
import edu.uclm.esi.juegos.entities.User;
import edu.uclm.esi.juegos.dto.ApiResponse;
import edu.uclm.esi.juegos.exhandling.UserAlreadyExistsException;
import edu.uclm.esi.juegos.exhandling.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody Map<String, String> userData) {
        String email = userData.get("email");
        String username = userData.get("username");
        String password = userData.get("password");
        try {
            User user = userService.registerUser(email, username, password);
            ApiResponse response = new ApiResponse("User registered successfully.", user.getId(), true);
            return ResponseEntity.ok(response);
        } catch (UserAlreadyExistsException ex) {
            ApiResponse errorResponse = new ApiResponse(ex.getMessage(), null, false);
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (Exception e) {
            ApiResponse errorResponse = new ApiResponse("An unexpected error occurred.", null, false);
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long id) {
        try {
            User user = userService.findUserById(id);
            ApiResponse response = new ApiResponse("User found successfully.", user, true);
            return ResponseEntity.ok(response);
        } catch (UserNotFoundException e) {
            ApiResponse errorResponse = new ApiResponse(e.getMessage(), null, false);
            return ResponseEntity.status(404).body(errorResponse);
        } catch (Exception e) {
            ApiResponse errorResponse = new ApiResponse("An unexpected error occurred.", null, false);
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getAllUsers() {
        try {
            ApiResponse response = new ApiResponse("Users retrieved successfully.", userService.getAllUsers(), true);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse errorResponse = new ApiResponse("Could not retrieve users.", null, false);
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
}
