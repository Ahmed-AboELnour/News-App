package com.news.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.news.model.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String email;
    @JsonIgnore
    private String password;
    private LocalDate dateOfBirth;
    @Enumerated(EnumType.STRING)
    private UserRole role;

}
