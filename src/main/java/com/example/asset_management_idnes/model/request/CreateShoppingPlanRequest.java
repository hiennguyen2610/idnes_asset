package com.example.asset_management_idnes.model.request;

import com.example.asset_management_idnes.statics.AssetStatus;
import com.example.asset_management_idnes.statics.AssetType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class CreateShoppingPlanRequest {

    String itemCode;  // Mã sản phẩm

    String productName;  //Tên sản phẩm

    AssetType assetType; // Loại hàng hóa/ dịch vụ

    String unit;  // Đơn vị

    String currency; // Loại tiền

    Integer contractDuration; // Thời hạn hợp đồng

    Integer quantity; // Số lượng

    Integer price; // Đơn giá

    AssetStatus status;  // Trạng thái

    LocalDate warrantyExpirationDate;  // Ngày kết thúc bảo hành

    LocalDate deliveryRequestDate; // Ngày yêu cầu giao hàng

    LocalDate deliveryDate; // Ngày giao hàng

    String contractCode;    // Mã hợp đồng

    LocalDate equipmentServiceReplacementDate;   // Ngày thay mới dịch vụ thiết bị

    String description; // Mô tả

    String supplierCode;    // Mã nhà cung cấp

    String supplier;    // Nhà cung cấp

    String productOrigin;   // Hãng sản xuất

    Set<Long> planListId;    // Danh sách (năm nào)

}
