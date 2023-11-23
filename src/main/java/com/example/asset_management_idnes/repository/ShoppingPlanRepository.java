package com.example.asset_management_idnes.repository;

import com.example.asset_management_idnes.domain.Asset;
import com.example.asset_management_idnes.domain.PlanList;
import com.example.asset_management_idnes.domain.ShoppingPlan;
import com.example.asset_management_idnes.statics.AssetStatus;
import com.example.asset_management_idnes.statics.AssetType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ShoppingPlanRepository extends JpaRepository<ShoppingPlan, Long> {

    Optional<ShoppingPlan> findAllByContractCode(String contractCode);

    List<ShoppingPlan> findAllByPlanLists(PlanList planList);

    @Query("SELECT sp FROM ShoppingPlan sp LEFT JOIN sp.planLists pl " +
            "WHERE (:itemCodeOrNameOrContractCode IS NULL OR (sp.itemCode LIKE %:itemCodeOrNameOrContractCode% OR sp.productName LIKE %:itemCodeOrNameOrContractCode% OR sp.contractCode LIKE %:itemCodeOrNameOrContractCode%)) " +
            "AND (:planListId IS NULL OR pl.id = :planListId) " +
            "AND (:type IS NULL OR sp.assetType = :type) " +
            "OR :itemCodeOrNameOrContractCode IS NULL")
    Page<ShoppingPlan> searchShoppingPlans(@Param("itemCodeOrNameOrContractCode") String itemCodeOrNameOrContractCode,
                                           @Param("planListId") Long planListId,
                                           @Param("type") AssetType type,
                                           Pageable pageable);


    @Query("SELECT sp FROM ShoppingPlan sp JOIN sp.planLists pl WHERE pl.id = :planListId")
    Page<ShoppingPlan> searchShoppingPlanByPlanListId(@Param("planListId") Long planListId, Pageable pageable);

}

