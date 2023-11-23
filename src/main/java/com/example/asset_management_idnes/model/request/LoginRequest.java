package com.example.asset_management_idnes.model.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginRequest {

    @NotBlank
    @Size(max = 50)
    @Email(message = "Email format is incorrect")
    String email;

    @NotBlank
    String password;

}
