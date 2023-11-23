package com.example.asset_management_idnes.model.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RefreshTokenRequest {

    @NotBlank
    String refreshToken;

}
