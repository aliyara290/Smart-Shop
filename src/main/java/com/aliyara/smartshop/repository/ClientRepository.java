package com.aliyara.smartshop.repository;

import com.aliyara.smartshop.model.Client;
import com.aliyara.smartshop.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {

    @EntityGraph(attributePaths = {"user", "orders", "orders.orderItems", "orders.promoCode"})
    Client getClientByUserId(UUID id);
}