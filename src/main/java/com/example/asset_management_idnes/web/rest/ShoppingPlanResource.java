package com.example.asset_management_idnes.web.rest;

import com.example.asset_management_idnes.domain.Asset;
import com.example.asset_management_idnes.domain.PlanList;
import com.example.asset_management_idnes.domain.ShoppingPlan;
import com.example.asset_management_idnes.exception.BadRequestException;
import com.example.asset_management_idnes.model.request.CreateShoppingPlanRequest;
import com.example.asset_management_idnes.model.request.UpdateAssetRequest;
import com.example.asset_management_idnes.model.request.UpdateShoppingPlanRequest;
import com.example.asset_management_idnes.repository.ShoppingPlanRepository;
import com.example.asset_management_idnes.service.AssetService;
import com.example.asset_management_idnes.service.ShoppingPlanService;
import com.example.asset_management_idnes.statics.AssetType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/shopping-plan")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShoppingPlanResource {

    AssetService assetService;

    ShoppingPlanService shoppingPlanService;

    ShoppingPlanRepository shoppingPlanRepository;


    // Lấy toàn bộ danh sách kế hoạch mua sắm
    @GetMapping("/get-all-shopping-plan")
    public ResponseEntity<Page<ShoppingPlan>> getAllShoppingPlan(Pageable pageable) {
        Page<ShoppingPlan> getAllShoppingPlanResponse = shoppingPlanService.getAll(pageable);
        return new ResponseEntity<>(getAllShoppingPlanResponse, HttpStatus.OK);
    }

    @GetMapping("/get-all-shopping-plan-not-pageable")
    public List<ShoppingPlan> getAllShoppingPlanNotPageable() {
        return shoppingPlanRepository.findAll();
    }


    // Tìm kiếm kế hoạch và hiển thị có phân trang
    @GetMapping("/search")
    public ResponseEntity<Page<ShoppingPlan>> searchAllShoppingPlan(@RequestParam(required = false) String itemCodeOrNameOrContractCode,
                                                                    @RequestParam(required = false) Long planListId,
                                                                    @RequestParam(required = false) AssetType type,
                                                                    Pageable pageable) {
        Page<ShoppingPlan> shoppingPlans = shoppingPlanService.searchAllShoppingPlan(itemCodeOrNameOrContractCode, planListId, type, pageable);
        return new ResponseEntity<>(shoppingPlans, HttpStatus.OK);
    }

    // Tạo mới kế hoạch
    @PostMapping("/create-shopping-plan")
    public ResponseEntity<?> createShoppingPlan(@RequestBody CreateShoppingPlanRequest createShoppingPlanRequest) throws BadRequestException {
        shoppingPlanService.createShoppingPlan(createShoppingPlanRequest);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    // Lấy sản phẩm theo id
    @GetMapping("get-shopping-plan/{id}")
    public ResponseEntity<?> getShoppingPlanById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(shoppingPlanService.findById(id));
    }

    // Cập nhật sản phẩm trong danh sách
    @PutMapping("/update-shopping-plan/{id}")
    public ResponseEntity<?> updateShoppingPlan(@PathVariable("id") Long id, @RequestBody @Valid UpdateShoppingPlanRequest updateShoppingPlanRequest) {
        shoppingPlanService.updateShoppingPlanRequest(id, updateShoppingPlanRequest);
        return ResponseEntity.ok(null);
    }

    // Thay đổi trạng thái kế hoạch
    @PostMapping("/change-plan-status")
    public void changePlanStatus(@RequestParam(value = "id") Long id, @RequestParam("status") String status) {
        shoppingPlanService.changeShoppingPlanStatus(id, status);
    }

    // Xóa kế hoạch
    @DeleteMapping("delete-shopping-plan/{id}")
    public ResponseEntity<?> deleteShoppingPlan(@PathVariable("id") Long id) {
        shoppingPlanService.deleteShoppingPlan(id);
        return ResponseEntity.noContent().build();
    }
}
