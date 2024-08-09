package com.news.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Data
public class AuthenticationResponse {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    public AuthenticationResponse(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
