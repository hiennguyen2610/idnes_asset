package com.example.asset_management_idnes.repository;

import com.example.asset_management_idnes.domain.Asset;
import com.example.asset_management_idnes.model.response.AssetResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Repository
@Transactional
public interface AssetRepository extends JpaRepository<Asset, Long> {

    Optional<Asset> findAllByContractCode(String contractCode);
}