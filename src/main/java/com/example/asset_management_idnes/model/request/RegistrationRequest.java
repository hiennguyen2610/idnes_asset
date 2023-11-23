package com.example.asset_management_idnes.model.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrationRequest {

    @NotBlank
    @Size(max = 50)
    String name;

    @NotBlank
    @Size(max = 50)
    String email;

    @NotBlank
    String password;

}
