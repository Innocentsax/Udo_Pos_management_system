package com.udo.service;

import com.udo.exceptions.UserException;
import com.udo.payload.DTO.UserDTO;
import com.udo.payload.response.AuthResponse;

public interface AuthService {
    AuthResponse signup(UserDTO userDTO) throws UserException;

    AuthResponse login(UserDTO userDTO) throws UserException;
}
