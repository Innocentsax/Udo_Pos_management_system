package com.udo.service.impl;

import com.udo.configuration.JwtProvider;
import com.udo.domain.UserRole;
import com.udo.exceptions.UserException;
import com.udo.mapper.UserMapper;
import com.udo.model.User;
import com.udo.payload.DTO.UserDTO;
import com.udo.payload.response.AuthResponse;
import com.udo.repository.UserRepository;
import com.udo.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserImplementation customUserImplementation;

    @Override
    public AuthResponse signup(UserDTO userDTO) throws UserException {
        User user = userRepository.findByEmail(userDTO.getEmail());
        if(user != null){
            throw new UserException("Email id already registered");
        }
        if(userDTO.getRole().equals(UserRole.ROLE_ADMIN)){
            throw new UserException("Role admin is not allowed");
        }

        User newUser = new User();
        newUser.setEmail(userDTO.getEmail());
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        newUser.setRole(userDTO.getRole());
        newUser.setFullName(userDTO.getFullName());
        newUser.setPhone(userDTO.getPhone());
        newUser.setLastLoginAt(LocalDateTime.now());
        newUser.setCreateAt(LocalDateTime.now());
        newUser.setUpdateAt(LocalDateTime.now());
        User savedUser = userRepository.save(newUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Registered Successfully");
        authResponse.setUserDTO(UserMapper.toDTO(savedUser));

        return authResponse;
    }

    @Override
    public AuthResponse login(UserDTO userDTO) throws UserException {
        String email = userDTO.getEmail();
        String password = userDTO.getPassword();

        Authentication authentication = authenticate(email, password);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.iterator().next().getAuthority();
        String jwt = jwtProvider.generateToken(authentication);
        User user = userRepository.findByEmail(email);
        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Login Successfully");
        authResponse.setUserDTO(UserMapper.toDTO(user));
        return authResponse;
    }

    private Authentication authenticate(String email, String password) throws UserException {
        UserDetails userDetails = customUserImplementation.loadUserByUsername(email);
        if(userDetails == null){
            throw new UserException("Email id doesn't exist" + email);
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new UserException("Password doesn't match");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
