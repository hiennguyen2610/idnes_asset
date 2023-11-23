package com.example.asset_management_idnes.model.request;

import com.example.asset_management_idnes.domain.Asset;
import com.example.asset_management_idnes.domain.PlanList;
import com.example.asset_management_idnes.statics.AssetStatus;
import com.example.asset_management_idnes.statics.AssetType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class CreatePlanListRequest {

   private String name;

}
