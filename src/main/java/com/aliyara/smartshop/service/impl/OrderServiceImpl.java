package com.aliyara.smartshop.service.impl;

import com.aliyara.smartshop.dto.request.OrderItemRequestDTO;
import com.aliyara.smartshop.dto.request.OrderRequestDTO;
import com.aliyara.smartshop.dto.response.OrderResponseDTO;
import com.aliyara.smartshop.enums.OrderStatus;
import com.aliyara.smartshop.exception.InsufficientStockException;
import com.aliyara.smartshop.exception.ResourceNotFoundException;
import com.aliyara.smartshop.mapper.OrderMapper;
import com.aliyara.smartshop.model.*;
import com.aliyara.smartshop.payload.ApiResponse;
import com.aliyara.smartshop.repository.ClientRepository;
import com.aliyara.smartshop.repository.OrderRepository;
import com.aliyara.smartshop.repository.ProductRepository;
import com.aliyara.smartshop.repository.PromoCodeRepository;
import com.aliyara.smartshop.service.interfaces.ClientService;
import com.aliyara.smartshop.service.interfaces.DiscountService;
import com.aliyara.smartshop.service.interfaces.OrderService;
import com.aliyara.smartshop.service.interfaces.PromoCodeService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class OrderServiceImpl implements OrderService {
    @Value("${vat.value}")
    private double vat;

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ClientRepository clientRepository;
    private final PromoCodeRepository promoCodeRepository;
    private final PromoCodeService promoCodeService;
    private final DiscountService discountService;
    private final ProductRepository productRepository;
    private final ClientService clientService;

    @Override
    public OrderResponseDTO create(OrderRequestDTO requestDTO) {
        Order order = new Order();
        order.setVAT(vat);
        Client client = clientRepository.findById(requestDTO.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found!"));
        clientService.updateClientLoyaltyLevel(client);
        order.setClient(client);

        double subtotal = 0.0;

        for (OrderItemRequestDTO orderItem : requestDTO.getOrderItems()) {
            Product product = productRepository.findById(orderItem.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product with ID" + orderItem.getProductId() + " not found!"));

            OrderItem item = new OrderItem();
            item.setQuantity(orderItem.getQuantity());
            item.setProduct(product);
            item.setOrder(order);
            item.setTotalLine(product.getUnitPrice() * orderItem.getQuantity());
            subtotal += item.getTotalLine();
            order.addItem(item);
        }

        order.setSubtotal(subtotal);

        if (requestDTO.getPromoCode() != null) {
            boolean isPromoCodeValid = promoCodeService.checkIfPromoCodeValid(requestDTO.getPromoCode());
            if (isPromoCodeValid) {
                PromoCode promoCode = promoCodeRepository.findByCode(requestDTO.getPromoCode()).get();
                promoCode.setUsedCount(promoCode.getUsedCount() + 1);
                order.setPromoCode(promoCode);
            }
        }

        double discount = discountService.calculateDiscount(order);
        order.setDiscount(discount);

        log.debug("subtotal now: {}", order.getSubtotal());
        double VAT = (order.getSubtotal() - order.getDiscount()) * (order.getVAT() / 100.0);
        log.debug("discount price now: {}", order.getDiscount());
        log.debug("vat now: {}", VAT);
        double finalPrice = (order.getSubtotal() - order.getDiscount()) + VAT;
        log.debug("final price now: {}", finalPrice);
        order.setTotal(finalPrice);
        log.debug("remaining price now: {}", order.getRemaining());
        order.setRemaining(finalPrice);
        log.debug("remaining price now: {}", order.getRemaining());
        Order savedOrder = orderRepository.save(order);

        checkIfStockAvailable(savedOrder);
        return orderMapper.toResponse(savedOrder);
    }

    @Override
    public OrderResponseDTO update(OrderRequestDTO requestDTO, UUID id) {

        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order with ID " + id + " not found!"));

        order.getOrderItems().clear();

        double subtotal = 0.0;

        for (OrderItemRequestDTO orderItem : requestDTO.getOrderItems()) {
            Product product = productRepository.findById(orderItem.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product with ID " + orderItem.getProductId() + " not found!"));

            OrderItem item = new OrderItem();
            item.setQuantity(orderItem.getQuantity());
            item.setProduct(product);
            item.setOrder(order);
            item.setTotalLine(product.getUnitPrice() * orderItem.getQuantity());

            subtotal += item.getTotalLine();
            order.addItem(item);
        }

        order.setSubtotal(subtotal);

        if (requestDTO.getPromoCode() != null && !Objects.equals(order.getPromoCode().getCode(), requestDTO.getPromoCode())) {
            if(order.getPromoCode() != null) {
                PromoCode oldPromoCode = order.getPromoCode();
                oldPromoCode.setUsedCount(oldPromoCode.getUsedCount() - 1);
                promoCodeRepository.save(oldPromoCode);
            }
            boolean isPromoCodeValid = promoCodeService.checkIfPromoCodeValid(requestDTO.getPromoCode());
            if (isPromoCodeValid) {
                PromoCode promoCode = promoCodeRepository.findByCode(requestDTO.getPromoCode()).orElseThrow(() -> new ResourceNotFoundException("Promo code not found!"));
                promoCode.setUsedCount(promoCode.getUsedCount() + 1);
                order.setPromoCode(promoCode);
            }
        }

        double discount = discountService.calculateDiscount(order);
        order.setDiscount(discount);

        double VAT = (order.getSubtotal() - order.getDiscount()) * (order.getVAT() / 100.0);
        double finalPrice = (order.getSubtotal() - order.getDiscount()) + VAT;

        order.setTotal(finalPrice);
        order.setRemaining(finalPrice);

        Order updatedOrder = orderRepository.save(order);

        checkIfStockAvailable(updatedOrder);
        return orderMapper.toResponse(updatedOrder);
    }


    @Override
    public ApiResponse<Void> softDelete(UUID id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order with ID " + id + " not found!"));
        orderRepository.delete(order);
        return new ApiResponse<>(true, "Order soft deleted successfully!", LocalDateTime.now(), null);
    }


    @Override
    public OrderResponseDTO findById(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order with ID " + id + " not found!"));
        return orderMapper.toResponse(order);
    }

    @Override
    public List<OrderResponseDTO> getAllOrders() {

        return orderRepository.findAll().stream().map(orderMapper::toResponse).toList();
    }

    @Transactional(noRollbackFor = InsufficientStockException.class)
    public void checkIfStockAvailable(Order order) {
        for (OrderItem orderItem : order.getOrderItems()) {
            Product product = orderItem.getProduct();
            if (product.getStock() < orderItem.getQuantity()) {
                order.setStatus(OrderStatus.REJECTED);
                if(order.getPromoCode() != null) {
                    PromoCode promoCode = order.getPromoCode();
                    promoCode.setUsedCount(promoCode.getUsedCount() - 1);
                    order.setPromoCode(promoCode);
                }
                orderRepository.save(order);
                throw new InsufficientStockException("Not enough stock for product: " + product.getName());
            }
        }
    }

    @Override
    public void decreaseProductsStock(Order order) {
        List<Product> productsToUpdate = new ArrayList<>();
        for (OrderItem item : order.getOrderItems()) {
            Product product = item.getProduct();
            product.setStock(product.getStock() - item.getQuantity());
            productsToUpdate.add(product);
        }
        productRepository.saveAll(productsToUpdate);
    }

    @Override
    public List<OrderResponseDTO> ordersHaveNoPayment() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .filter(o -> o.getPayments().isEmpty())
                .map(orderMapper::toResponse)
                .toList();
    };
}
