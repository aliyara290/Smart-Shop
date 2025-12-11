package com.aliyara.smartshop.repository;

import com.aliyara.smartshop.enums.OrderStatus;
import com.aliyara.smartshop.model.Client;
import com.aliyara.smartshop.model.Order;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    long countByClientIdAndStatus(UUID uuid, OrderStatus status);

    @Query("SELECT SUM(o.total) FROM Order o WHERE o.client.id = :clientId AND o.status = 'CONFIRMED'")
    Double getConfirmedTotal(@Param("clientId") UUID clientId);

    @Query("""
            SELECT o FROM Order o LEFT JOIN o.payments p group by o having COUNT(p) = 0
            """)
    List<Order> ordersThatHaveMoreThanOnePayment();

}