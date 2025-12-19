package com.example.labOdc.Service;

import java.util.List;

import com.example.labOdc.DTO.UserDTO;
import com.example.labOdc.Model.User;

public interface UserService {
    User createUser(UserDTO userDTO);

    List<User> getAllUser();

    void deleteUser(Long id);

    User getUserById(Long id);

    User updateUser(UserDTO userDTO, Long id);
}
