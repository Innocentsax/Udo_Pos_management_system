package com.udo.mapper;

import com.udo.model.User;
import com.udo.payload.DTO.UserDTO;

public class UserMapper {

    public static UserDTO toDTO(User savedUser){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(savedUser.getId());
        userDTO.setFullName(savedUser.getFullName());
        userDTO.setEmail(savedUser.getEmail());
        userDTO.setRole(savedUser.getRole());
        userDTO.setCreatedAt(savedUser.getCreateAt());
        userDTO.setUpdatedAt(savedUser.getUpdateAt());
        userDTO.setLastLogin(savedUser.getLastLoginAt());
        userDTO.setPhone(savedUser.getPhone());
        userDTO.setFullName(savedUser.getFullName());
        userDTO.setPassword(maskPassword(userDTO.getPassword()));
        return userDTO;
    }

    public static String maskPassword(String password){
        if(password == null || password.length() <= 3){
            return "************";
        }
        int visibleChars = 3;
        String visible = password.substring(0, visibleChars);
        String masked = "*".repeat(password.length() - visibleChars);
        return visible + masked;
    }
}
