package com.udo.model;

import com.udo.domain.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    @Email(message = "Email should be valid")
    private String email;

    @ManyToOne
    private Store store;

    private String phone;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private UserRole role;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private LocalDateTime lastLoginAt;
}
