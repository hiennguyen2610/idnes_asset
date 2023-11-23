package com.example.asset_management_idnes.model.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.sql.Date;
import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssetResponse {

    Long id;

    String productName;


    String productOrigin;


    String serial;


    String description;


    String status;


    LocalDate purchase_date;
}
