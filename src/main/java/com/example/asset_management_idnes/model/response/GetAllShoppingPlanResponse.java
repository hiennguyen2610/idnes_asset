package com.example.asset_management_idnes.model.response;

import com.example.asset_management_idnes.statics.AssetStatus;
import com.example.asset_management_idnes.statics.AssetType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.sql.Date;
import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetAllShoppingPlanResponse {

    String itemCode;  // Mã sản phẩm

    String productName;  //Tên sản phẩm

    AssetType assetType; // Loại hàng hóa/ dịch vụ

    String unit;  // Đơn vị

    String currency; // Loại tiền

    Integer contractDuration; // Thời hạn hợp đồng

    Integer quantity; // Số lượng

    Integer price; // Đơn giá

    AssetStatus status;  // Trạng thái

    Date warrantyExpirationDate;  // Ngày kết thúc bảo hành

    Date deliveryRequestDate; // Ngày yêu cầu giao hàng

    Date deliveryDate; // Ngày giao hàng

    String contractCode;    // Mã hợp đồng

    Date equipmentServiceReplacementDate;   // Ngày thay mới dịch vụ thiết bị

    String description; // Mô tả

    String supplierCode;    // Mã nhà cung cấp

    String supplier;    // Nhà cung cấp

    String productOrigin;   // Hãng sản xuất

    Set<Long> planListId;    // Danh sách (năm nào)

}
