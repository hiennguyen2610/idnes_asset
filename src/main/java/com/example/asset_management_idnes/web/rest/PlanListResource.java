package com.example.asset_management_idnes.web.rest;

import com.example.asset_management_idnes.domain.PlanList;
import com.example.asset_management_idnes.domain.ShoppingPlan;
import com.example.asset_management_idnes.exception.BadRequestException;
import com.example.asset_management_idnes.model.request.CreatePlanListRequest;
import com.example.asset_management_idnes.model.request.UpdatePlanListRequest;
import com.example.asset_management_idnes.model.response.ErrorResponse;
import com.example.asset_management_idnes.repository.PlanListRepository;
import com.example.asset_management_idnes.service.PlanService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/plan-list")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PlanListResource {

    PlanService planService;

    PlanListRepository planListRepository;

    // Tạo danh sách
    @PostMapping("/create-plan-list")
    public ResponseEntity<?> createPlanList(@RequestBody CreatePlanListRequest createPlanListRequest) {
        try {
            PlanList planList = planService.createList(createPlanListRequest.getName());
            return ResponseEntity.ok(planList);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    // Lấy danh sách
    @GetMapping("/get-all-plan-list")
    public List<PlanList> getAllPlanList() {
        return planListRepository.findAll();
    }


    // Xóa danh sách
    @DeleteMapping("delete-plan-list/{id}")
    public ResponseEntity<?> deletePlanList(@PathVariable("id") Long id) throws BadRequestException {
        planService.deletePlanList(id);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    // Lấy danh sách theo id
    @GetMapping("/get-plan-list/{id}")
    public ResponseEntity<?> getPlanListById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(planService.getPlanListById(id));
    }

    // Cập nhật danh sách
    @PutMapping("/update-plan-list/{id}")
    public ResponseEntity<?> updatePlanList(@PathVariable("id") Integer id, @RequestBody UpdatePlanListRequest updatePlanListRequest) {
        try {
            PlanList planList = planService.updatePlanList(id, updatePlanListRequest.getName());
            return ResponseEntity.ok(planList);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
