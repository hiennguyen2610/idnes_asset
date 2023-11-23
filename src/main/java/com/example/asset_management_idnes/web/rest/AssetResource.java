package com.example.asset_management_idnes.web.rest;

import com.example.asset_management_idnes.domain.Asset;
import com.example.asset_management_idnes.model.request.CreateAssetRequest;
import com.example.asset_management_idnes.model.request.UpdateAssetRequest;
import com.example.asset_management_idnes.service.AssetService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/asset")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AssetResource {

    AssetService assetService;

    // Lấy toàn bộ danh sách sản phẩm
    @GetMapping("/get-all-asset")
    public ResponseEntity<Page<Asset>> getAllAssets(Pageable pageable) {
        Page<Asset> assetResponses = assetService.getAllAssets(pageable);
        return new ResponseEntity<>(assetResponses, HttpStatus.OK);
    }

    // Tạo mới sp
    @PostMapping("/create-asset")
    public ResponseEntity<?> createAsset(@RequestBody CreateAssetRequest request) {
        assetService.createAsset(request);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    // Xóa sp
    @DeleteMapping("/delete-asset/{id}")
    public ResponseEntity<?> deleteAsset(@PathVariable Long id){
        assetService.deleteAsset(id);
        return new ResponseEntity<>("Xóa asset thành công", HttpStatus.ACCEPTED);
    }

    // Lấy asset theo id
    @GetMapping("/asset/{id}")
    public ResponseEntity<?> getAsset(@PathVariable("id") Long id) {
        return ResponseEntity.ok(assetService.findById(id));
    }

    // Cập nhật asset
    @PutMapping("/update-asset/{id}")
    public ResponseEntity<?> updateAsset(@PathVariable("id") Long id, @RequestBody @Valid UpdateAssetRequest updateAssetRequest) {
        assetService.updateAsset(id, updateAssetRequest);
        return ResponseEntity.ok(null);
    }


//     Test send email
    @GetMapping("/send-warranty-expiration-notifications")
    public void sendWarrantyExpirationNotifications() throws MessagingException {
        assetService.sendWarrantyExpirationNotifications();
    }
}
