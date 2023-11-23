package com.example.asset_management_idnes.repository;

import com.example.asset_management_idnes.domain.PlanList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanListRepository extends JpaRepository<PlanList, Long> {
    boolean existsByName(String name);
}
