package com.example.asset_management_idnes.service;

import com.example.asset_management_idnes.domain.Asset;
import com.example.asset_management_idnes.domain.PlanList;
import com.example.asset_management_idnes.domain.ShoppingPlan;
import com.example.asset_management_idnes.exception.NotFoundException;
import com.example.asset_management_idnes.model.request.CreateAssetRequest;
import com.example.asset_management_idnes.model.request.CreatePlanListRequest;
import com.example.asset_management_idnes.model.request.UpdateAssetRequest;
import com.example.asset_management_idnes.model.response.AssetResponse;
import com.example.asset_management_idnes.repository.AssetRepository;
import com.example.asset_management_idnes.repository.ShoppingPlanRepository;
import com.example.asset_management_idnes.statics.AssetStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dockerjava.api.exception.BadRequestException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class AssetService {

    AssetRepository assetRepository;

    final ObjectMapper objectMapper;

    private EmailService emailService;

    final PlanService planService;

    public List<AssetResponse> getAll() {
        List<Asset> assetList = assetRepository.findAll();
        if (!CollectionUtils.isEmpty(assetList)) {
            return assetList.stream().map(u -> objectMapper.convertValue(u, AssetResponse.class)).collect(Collectors.toList());
        }
        System.out.println("get all asset ok");
        return Collections.emptyList();
    }


    public void createAsset(CreateAssetRequest request) throws BadRequestException {
        Optional<Asset> assetOptional = assetRepository.findAllByContractCode(request.getContractCode());
        if (assetOptional.isPresent()) {
            throw new BadRequestException("Sản phẩm này đã trùng hợp đồng");
        }

        Set<PlanList> planListSet = new HashSet<>();
        request.getPlanListId().forEach(id -> planListSet.add(planService.findById(id).get()));

        Asset asset = Asset.builder()
                .itemCode(request.getItemCode())
                .productName(request.getProductName())
                .assetType(request.getAssetType())
                .unit(request.getUnit())
                .currency(request.getCurrency())
                .contractDuration(request.getContractDuration())
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .warrantyExpirationDate(request.getWarrantyExpirationDate())
                .deliveryRequestDate(request.getDeliveryRequestDate())
                .deliveryDate(request.getDeliveryDate())
                .contractCode(request.getContractCode())
                .equipmentServiceReplacementDate(request.getEquipmentServiceReplacementDate())
                .description(request.getDescription())
                .supplierCode(request.getSupplierCode())
                .supplier(request.getSupplier())
                .productOrigin(request.getProductOrigin())
                .planLists(planListSet)
                .build();
        assetRepository.save(asset);
    }


    public void deleteAsset(Long id) throws BadRequestException {
        Optional<Asset> assetOptional = assetRepository.findById(id);
        if (!assetOptional.isPresent()) {
            throw new NotFoundException("Không tìm thấy dịch vụ này!");
        }

        // Todo: thêm điều kiện ko thể xóa user nếu user đó đang sử dụng tài sản
        assetRepository.deleteById(id);
    }


    // Tìm asset theo id
    public Asset findById(Long id) {
        return assetRepository.findById(id).orElse(null);
    }

    public void updateAsset(Long id, UpdateAssetRequest updateAssetRequest) {
        Asset asset = assetRepository.findById(id).orElse(null);
        if (asset != null) {

            asset.setProductName(updateAssetRequest.getProductName());
            asset.setItemCode(updateAssetRequest.getSerial());
            asset.setDescription(updateAssetRequest.getDescription());
            asset.setProductOrigin(updateAssetRequest.getProductOrigin());
            assetRepository.save(asset);

        }
    }


    public Page<Asset> getAllAssets(Pageable pageable) {
        return assetRepository.findAll(pageable);
    }


    final ShoppingPlanRepository shoppingPlanRepository;


    // Gửi email thông báo khi sản phẩm hết hạn
    public void sendWarrantyExpirationNotifications() throws MessagingException {
        List<Asset> assets = assetRepository.findAll(); // Lấy tất cả các tài sản

        for (Asset asset : assets) {
            if (asset.getWarrantyExpirationDate().isEqual(LocalDate.now())) {
                String productName = asset.getProductName();

                String subject = "Thông báo về sản phẩm hết hạn bảo hành";
                String text = "Sản phẩm " + productName + " của bạn đã hết hạn bảo hành.";
                emailService.sendEmailToRecipients(subject, text);
            }
        }
    }
}
