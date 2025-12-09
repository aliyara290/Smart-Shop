package com.aliyara.smartshop.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "check_payment")
public class CheckPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "check_number", nullable = false, unique = true)
    private String checkNumber;

    @Column(name = "issuer_name", nullable = false)
    private String issuerName;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "payment_id", unique = true)
    private Payment payment;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

}
