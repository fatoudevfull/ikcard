package com.BackendIkcard.IkcardBackend.Message.Requette;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "Username is required and must not be empty")
    private String username;

    @NotBlank(message = "Password is required and must not be empty")
    private String password;

    @NotBlank(message = "Email is required and must not be empty")
    private String email;
}

