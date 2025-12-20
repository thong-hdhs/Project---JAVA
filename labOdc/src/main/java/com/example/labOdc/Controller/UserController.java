package com.example.labOdc.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.labOdc.APi.ApiResponse;
import com.example.labOdc.DTO.UserDTO;
import com.example.labOdc.DTO.Response.UserResponse;
import com.example.labOdc.Model.User;
import com.example.labOdc.Service.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/")
    public ApiResponse<UserResponse> createUser(@Valid @RequestBody UserDTO userDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage).toList();
            return ApiResponse.error(errorMessages);
        }
        User user = userService.createUser(userDTO);
        return ApiResponse.success(UserResponse.fromUser(user), "Thanh cong", HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ApiResponse<List<UserResponse>> getAllUser() {
        List<User> list = userService.getAllUser();
        return ApiResponse.success(list.stream().map(UserResponse::fromUser).toList(), "Thanh cong", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(String.format("Xoa thanh cong"));
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ApiResponse.success(UserResponse.fromUser(user), "Thanh cong", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ApiResponse<UserResponse> updateUser(@Valid @RequestBody UserDTO userDTO, @PathVariable Long id) {
        User user = userService.updateUser(userDTO, id);
        return ApiResponse.success(UserResponse.fromUser(user), "Thanh cong", HttpStatus.OK);
    }
}
