package com.aliyara.smartshop.model;

import com.aliyara.smartshop.model.enums.ClientLoyaltyLevel;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "loyalty_level", nullable = false)
    private ClientLoyaltyLevel loyaltyLevel;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<Order> orders;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
}