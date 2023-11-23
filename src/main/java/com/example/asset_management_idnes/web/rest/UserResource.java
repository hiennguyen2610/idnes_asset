package com.example.asset_management_idnes.web.rest;

import com.example.asset_management_idnes.model.request.RegistrationRequest;
import com.example.asset_management_idnes.model.response.UserResponse;
import com.example.asset_management_idnes.repository.UserRepository;
import com.example.asset_management_idnes.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserResource {

    UserService userService;

    UserRepository userRepository;

    // Lấy danh sách user
    @GetMapping
    public List<UserResponse> getAll() {
        return userService.getAll();
    }

    // Lấy thông tin user theo id
    @GetMapping("/{id}")
    public UserResponse getDetail(@PathVariable Long id) throws ClassNotFoundException {
        return userService.getDetail(id);
    }

    // Xóa user
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleterUser(id);
        return new ResponseEntity<>("Xóa user thành công", HttpStatus.ACCEPTED);
    }

    // Tạo mới user
    @PostMapping("/create-user")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationRequest request) {
        return userRepository.findByEmail(request.getEmail())
                .map(user -> new ResponseEntity<>("Email is existed", HttpStatus.BAD_REQUEST))
                .orElseGet(() -> {
                    userService.registerUser(request);
                    return new ResponseEntity<>(null, HttpStatus.CREATED);
                });
    }
}
