package com.example.asset_management_idnes.domain;

import com.example.asset_management_idnes.statics.AssetStatus;
import com.example.asset_management_idnes.statics.AssetType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shopping_plan")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShoppingPlan extends BaseEntity {

    @Column(name = "item_code")
    String itemCode;  // Mã sản phẩm

    @Column(name = "name")
    String productName;  //Tên sản phẩm

    @Column(name = "type")
    AssetType assetType; // Loại hàng hóa/ dịch vụ

    @Column(name = "unit")
    String unit;  // Đơn vị

    @Column(name = "currency")
    String currency; // Loại tiền

    @Column(name = "contract_duration")
    Integer contractDuration; // Thời hạn hợp đồng

    @Column(name = "quantity")
    Integer quantity; // Số lượng

    @Column(name = "price")
    Integer price; // Đơn giá

    @Column(name = "status")
    AssetStatus status;  // Trạng thái

    @Column(name = "warranty_expiration_date")
    LocalDate warrantyExpirationDate;  // Ngày kết thúc bảo hành

    @Column(name = "delivery_request_date")
    LocalDate deliveryRequestDate; // Ngày yêu cầu giao hàng

    @Column(name = "delivery_date")
    LocalDate deliveryDate; // Ngày giao hàng

    @Column(name = "contract_code")
    String contractCode;    // Mã hợp đồng

    @Column(name = "equipment_service_replacement_date")
    LocalDate equipmentServiceReplacementDate;   // Ngày thay mới dịch vụ thiết bị

    @Column(name = "description")
    String description; // Mô tả

    @Column(name = "supplier_code")
    String supplierCode;    // Mã nhà cung cấp

    @Column(name = "supplier")
    String supplier;    // Nhà cung cấp

    @Column(name = "origin")
    String productOrigin;   // Hãng sản xuất

    @ManyToMany
    @JoinTable(name = "shoppingplan_planlist",
            joinColumns = @JoinColumn(name = "shopping_plan_id"),
            inverseJoinColumns = @JoinColumn(name = "plan_list_id"))
     Set<PlanList> planLists;

}
