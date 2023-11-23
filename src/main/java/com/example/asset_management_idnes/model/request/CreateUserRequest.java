package com.example.asset_management_idnes.model.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUserRequest {

    @NotBlank
    @Length(max = 50, message = "username không được vượt quá 50 ký tự")
    String email;

    @NotBlank
    String password;

}
