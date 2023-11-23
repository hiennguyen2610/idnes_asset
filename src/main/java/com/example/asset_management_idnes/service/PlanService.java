package com.example.asset_management_idnes.service;

import com.example.asset_management_idnes.domain.PlanList;
import com.example.asset_management_idnes.domain.ShoppingPlan;
import com.example.asset_management_idnes.exception.BadRequestException;
import com.example.asset_management_idnes.exception.NotFoundException;
import com.example.asset_management_idnes.repository.PlanListRepository;
import com.example.asset_management_idnes.repository.ShoppingPlanRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlanService {

    final PlanListRepository planListRepository;

    final ShoppingPlanRepository shoppingPlanRepository;

    public PlanList createList(String name) throws RuntimeException {
        if (planListRepository.existsByName(name)) {
            throw new RuntimeException("List đã tồn tại");
        }
        PlanList planList = new PlanList();
        planList.setName(name);
        return planListRepository.save(planList);
    }

    public Optional<PlanList> findById(Long id) {
        return planListRepository.findById(id);
    }


    // Xóa plan list
    public void deletePlanList(Long id) throws BadRequestException {
        Optional<PlanList> planListOptional = planListRepository.findById(id);
        if (!planListOptional.isPresent()) {
            throw new NotFoundException("Không tìm thấy danh sách kế hoạch");
        }
        List<ShoppingPlan> shoppingPlanList = shoppingPlanRepository.findAllByPlanLists(planListOptional.get());
        if (shoppingPlanList.size() > 0) {
            throw new BadRequestException("Không thể xóa vì đang có sản phẩm trong danh sách");
        }
        planListRepository.deleteById(id);
    }

    public PlanList getPlanListById(Integer id) {
        return planListRepository.findById(id.longValue()).orElse(null);
    }

    public PlanList updatePlanList(Integer id, String name)throws RuntimeException {
        PlanList planList = getPlanListById(id);
        for (PlanList pl : planListRepository.findAll()) {
            if (pl.getName().equals(name)) {
                throw new RuntimeException("Tên list đã tồn tại");
            }
        }
        planList.setName(name);
        return planListRepository.save(planList);
    }

    public Page<PlanList> getAll(Pageable pageable) {
        return planListRepository.findAll(pageable);
    }
}
