package com.example.asset_management_idnes.service;

import com.example.asset_management_idnes.domain.Asset;
import com.example.asset_management_idnes.domain.PlanList;
import com.example.asset_management_idnes.domain.ShoppingPlan;
import com.example.asset_management_idnes.exception.BadRequestException;
import com.example.asset_management_idnes.exception.NotFoundException;
import com.example.asset_management_idnes.model.request.CreateShoppingPlanRequest;
import com.example.asset_management_idnes.model.request.UpdateShoppingPlanRequest;
import com.example.asset_management_idnes.repository.AssetRepository;
import com.example.asset_management_idnes.repository.PlanListRepository;
import com.example.asset_management_idnes.repository.ShoppingPlanRepository;
import com.example.asset_management_idnes.statics.AssetStatus;
import com.example.asset_management_idnes.statics.AssetType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Email;
import java.util.*;

@Async
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShoppingPlanService {
    final ShoppingPlanRepository shoppingPlanRepository;

    final PlanListRepository planListRepository;

    final PlanService planService;

    final AssetRepository assetRepository;


    // Tạo shopping-plan
    public void createShoppingPlan(CreateShoppingPlanRequest createShoppingPlanRequest) throws BadRequestException {
        Optional<ShoppingPlan> shoppingPlanOptional = shoppingPlanRepository.findAllByContractCode(createShoppingPlanRequest.getContractCode());
        if (shoppingPlanOptional.isPresent()) {
            throw new BadRequestException("Sản phẩm này đã trùng hợp đồng");
        }

        Set<PlanList> planListSet = new HashSet<>();
        createShoppingPlanRequest.getPlanListId().forEach(id -> planListSet.add(planService.findById(id).get()));

        ShoppingPlan shoppingPlan = ShoppingPlan.builder()
                .itemCode(createShoppingPlanRequest.getItemCode())
                .productName(createShoppingPlanRequest.getProductName())
                .assetType(createShoppingPlanRequest.getAssetType())
                .unit(createShoppingPlanRequest.getUnit())
                .currency(createShoppingPlanRequest.getCurrency())
                .contractDuration(createShoppingPlanRequest.getContractDuration())
                .quantity(createShoppingPlanRequest.getQuantity())
                .price(createShoppingPlanRequest.getPrice())
                .status(AssetStatus.DOING)
                .warrantyExpirationDate(createShoppingPlanRequest.getWarrantyExpirationDate())
                .deliveryRequestDate(createShoppingPlanRequest.getDeliveryRequestDate())
                .deliveryDate(createShoppingPlanRequest.getDeliveryDate())
                .contractCode(createShoppingPlanRequest.getContractCode())
                .equipmentServiceReplacementDate(createShoppingPlanRequest.getEquipmentServiceReplacementDate())
                .description(createShoppingPlanRequest.getDescription())
                .supplierCode(createShoppingPlanRequest.getSupplierCode())
                .supplier(createShoppingPlanRequest.getSupplier())
                .productOrigin(createShoppingPlanRequest.getProductOrigin())
                .planLists(planListSet)
                .build();
        shoppingPlanRepository.save(shoppingPlan);
    }

    public void changeShoppingPlanStatus(Long id, String status) {
        Optional<ShoppingPlan> shoppingPlanOptional = shoppingPlanRepository.findById(id);

        if (shoppingPlanOptional.isPresent()) {
            ShoppingPlan shoppingPlan = shoppingPlanOptional.get();

            for (AssetStatus s : AssetStatus.values()) {
                if (s.getName().equals(status)) {
                    shoppingPlan.setStatus(s);
                    shoppingPlanRepository.save(shoppingPlan);

                    // Tạo một Asset mới và sao chép thông tin từ ShoppingPlan
                    Asset asset = new Asset();
                    asset.copyFromShoppingPlan(shoppingPlan);
                    asset.copyPlanListsFromShoppingPlan(shoppingPlan);
                    // Lưu đối tượng Asset mới vào AssetRepository
                    assetRepository.save(asset);

                    return;
                }
            }
        }
    }


    // Thay đổi trạng thái shopping plan (doing -> done)
    public void changePlanStatus(Long id, String status) {
        Optional<ShoppingPlan> shoppingPlanOptional = shoppingPlanRepository.findById(id);

        if (shoppingPlanOptional.isPresent()) {
            for (AssetStatus s : AssetStatus.values()) {
                if (s.getName().equals(status)) {
                    shoppingPlanOptional.get().setStatus(s);
                    shoppingPlanRepository.save(shoppingPlanOptional.get());
                    Asset asset = new Asset();
                    ShoppingPlan shoppingPlan = shoppingPlanOptional.get();

                    asset.setItemCode(shoppingPlan.getItemCode());
                    asset.setProductName(shoppingPlan.getProductName());
                    asset.setAssetType(shoppingPlan.getAssetType());
                    asset.setUnit(shoppingPlan.getUnit());
                    asset.setCurrency(shoppingPlan.getCurrency());
                    asset.setContractDuration(shoppingPlan.getContractDuration());
                    asset.setQuantity(shoppingPlan.getQuantity());
                    asset.setPrice(shoppingPlan.getPrice());
                    asset.setWarrantyExpirationDate(shoppingPlan.getWarrantyExpirationDate());
                    asset.setDeliveryRequestDate(shoppingPlan.getDeliveryRequestDate());
                    asset.setDeliveryDate(shoppingPlan.getDeliveryDate());
                    asset.setContractCode(shoppingPlan.getContractCode());
                    asset.setEquipmentServiceReplacementDate(shoppingPlan.getEquipmentServiceReplacementDate());
                    asset.setDescription(shoppingPlan.getDescription());
                    asset.setSupplierCode(shoppingPlan.getSupplierCode());
                    asset.setSupplier(shoppingPlan.getSupplier());
                    asset.setProductOrigin(shoppingPlan.getProductOrigin());
                    asset.setPlanLists(shoppingPlan.getPlanLists());
                    assetRepository.save(asset);
                    return;
                }
            }
        }
    }


    // Thay đổi thông tin shopping-plan
    public void updateShoppingPlanRequest(Long id, UpdateShoppingPlanRequest updateShoppingPlanRequest) {
        ShoppingPlan shoppingPlan = shoppingPlanRepository.findById(id).orElse(null);

        Set<PlanList> planListSet = new LinkedHashSet<>();
        for (Long planList : updateShoppingPlanRequest.getPlanListId()) {
            planListSet.add(planListRepository.findById(planList).orElse(null));
        }

        if (shoppingPlan != null) {
            shoppingPlan.setItemCode(updateShoppingPlanRequest.getItemCode());
            shoppingPlan.setProductName(updateShoppingPlanRequest.getProductName());
            shoppingPlan.setAssetType(updateShoppingPlanRequest.getAssetType());
            shoppingPlan.setUnit(updateShoppingPlanRequest.getUnit());
            shoppingPlan.setCurrency(updateShoppingPlanRequest.getCurrency());
            shoppingPlan.setContractDuration(updateShoppingPlanRequest.getContractDuration());
            shoppingPlan.setQuantity(updateShoppingPlanRequest.getQuantity());
            shoppingPlan.setPrice(updateShoppingPlanRequest.getPrice());
            shoppingPlan.setWarrantyExpirationDate(updateShoppingPlanRequest.getWarrantyExpirationDate());
            shoppingPlan.setDeliveryRequestDate(updateShoppingPlanRequest.getDeliveryRequestDate());
            shoppingPlan.setDeliveryDate(updateShoppingPlanRequest.getDeliveryDate());
            shoppingPlan.setContractCode(updateShoppingPlanRequest.getContractCode());
            shoppingPlan.setEquipmentServiceReplacementDate(updateShoppingPlanRequest.getEquipmentServiceReplacementDate());
            shoppingPlan.setDescription(updateShoppingPlanRequest.getDescription());
            shoppingPlan.setSupplierCode(updateShoppingPlanRequest.getSupplierCode());
            shoppingPlan.setSupplier(updateShoppingPlanRequest.getSupplier());
            shoppingPlan.setProductOrigin(updateShoppingPlanRequest.getProductOrigin());
            shoppingPlan.setPlanLists(planListSet);
            shoppingPlanRepository.save(shoppingPlan);
        }
    }


    // Lấy thông tin shopping-plan theo id
    public ShoppingPlan findById(Long id) {
        return shoppingPlanRepository.findById(id).orElse(null);
    }

    public void deleteShoppingPlan(Long id) throws NotFoundException {
        ShoppingPlan shoppingPlan = shoppingPlanRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm này"));
        shoppingPlanRepository.delete(shoppingPlan);
    }


    // Lấy danh sách sản phẩm
    public Page<ShoppingPlan> getAll(Pageable pageable) {
        return shoppingPlanRepository.findAll(pageable);
    }


    // Tìm kiếm và phân trang
    public Page<ShoppingPlan> searchAllShoppingPlan(String itemCodeOrNameOrContractCode, Long planListId, AssetType type, Pageable pageable) {
        Page<ShoppingPlan> shoppingPlanList = null;

        if (planListId != null && itemCodeOrNameOrContractCode == null && type == null) {
            // Tìm kiếm theo list
            shoppingPlanList = shoppingPlanRepository.searchShoppingPlanByPlanListId(planListId, pageable);
        } else {
            // Tìm kiếm bình thường
            shoppingPlanList = shoppingPlanRepository.searchShoppingPlans('%' + itemCodeOrNameOrContractCode + '%', planListId, type, pageable);
        }

        return shoppingPlanList;
    }


}
