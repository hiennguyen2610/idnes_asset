package com.example.asset_management_idnes.statics;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AssetType {

    HARDWARE("Phần cứng"),
    SOFTWARE("Phần mềm"),
    SERVICE("Dịch vụ");

    public String name;

    @JsonValue
    public String toName() {
        return name;
    }
}
