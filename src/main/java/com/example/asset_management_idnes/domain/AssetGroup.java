package com.example.asset_management_idnes.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "plan_list")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssetGroup extends BaseEntity {
    @Column(name = "name")
    String name;
}
