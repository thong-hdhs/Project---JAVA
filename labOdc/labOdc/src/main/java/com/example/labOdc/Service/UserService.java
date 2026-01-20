package com.example.labOdc.Service;

import java.util.List;

import com.example.labOdc.DTO.UserDTO;
import com.example.labOdc.Model.User;

public interface UserService {
    User createUser(UserDTO userDTO);

    List<User> getAllUser();

    void deleteUser(String id);

    User getUserById(String id);

    User updateUser(UserDTO userDTO, String id);
}
