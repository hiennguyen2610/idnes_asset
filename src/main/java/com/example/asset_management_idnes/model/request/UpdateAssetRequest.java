package com.example.asset_management_idnes.model.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateAssetRequest {

    String productName;


    String productOrigin;


    String serial;


    String description;


    String status;


    LocalDate purchase_date;

}
