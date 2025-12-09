package com.aliyara.smartshop.repository;

import com.aliyara.smartshop.model.CheckPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CheckPaymentRepository extends JpaRepository<CheckPayment, UUID> {
}
