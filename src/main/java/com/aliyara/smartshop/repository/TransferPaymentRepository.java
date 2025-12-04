package com.aliyara.smartshop.repository;

import com.aliyara.smartshop.model.CashPayment;
import com.aliyara.smartshop.model.TransferPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransferPaymentRepository extends JpaRepository<TransferPayment, UUID> {
}
