package com.example.asset_management_idnes.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "plan_list")
@FieldDefaults(level = AccessLevel.PRIVATE)

public class PlanList extends BaseEntity{

    @Column(name = "name")
    String name;

}
