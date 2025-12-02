package com.aliyara.smartshop.repository;

import com.aliyara.smartshop.model.PromoCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PromoCodeRepository extends JpaRepository<PromoCode, UUID> {
    boolean existsPromoCodeByCode(String code);
    Optional<PromoCode> findByCode(String promoCode);
}
