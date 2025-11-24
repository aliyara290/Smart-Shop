package com.aliyara.smartshop.model;

import com.aliyara.smartshop.model.enums.ClientLoyaltyLevel;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "clients")
@PrimaryKeyJoinColumn(name = "id")
public class Client extends User {

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "loyalty_level", nullable = false)
    private ClientLoyaltyLevel loyaltyLevel = ClientLoyaltyLevel.BASIC;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<Order> orders;
}