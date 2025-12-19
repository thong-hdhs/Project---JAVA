package com.example.labOdc.Service.Implement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.labOdc.DTO.UserDTO;
import com.example.labOdc.Exception.ResourceNotFoundException;
import com.example.labOdc.Model.User;
import com.example.labOdc.Repository.UserRepository;
import com.example.labOdc.Service.UserService;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Service
@AllArgsConstructor
@Builder
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User createUser(UserDTO userDTO) {
        User user = User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .role(userDTO.getRole())
                .build();
        userRepository.save(user);
        return user;
    }

    @Override
    public List<User> getAllUser() {
        List<User> list = userRepository.findAll();
        return list;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ko thay id"));
        return user;
    }

    @Override
    public User updateUser(UserDTO userDTO, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ko thay id"));

        user.setName(userDTO.getName());
        user.setEmail(userDTO.getName());
        user.setRole(userDTO.getRole());
        userRepository.save(user);
        return user;
    }

}
