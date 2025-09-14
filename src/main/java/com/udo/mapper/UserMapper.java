package com.udo.mapper;

import com.udo.model.User;
import com.udo.payload.DTO.UserDTO;

public class UserMapper {

    public static UserDTO toDTO(User savedUser){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(savedUser.getId());
        userDTO.setEmail(savedUser.getEmail());
        userDTO.setRole(savedUser.getRole());
        userDTO.setCreatedAt(savedUser.getCreateAt());
        userDTO.setUpdatedAt(savedUser.getUpdateAt());
        userDTO.setLastLogin(savedUser.getLastLoginAt());
        userDTO.setPhone(savedUser.getPhone());
        userDTO.setFullName(savedUser.getFullName());
        return userDTO;
    }
}
