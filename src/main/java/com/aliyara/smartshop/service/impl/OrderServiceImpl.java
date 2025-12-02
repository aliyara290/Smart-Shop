package com.aliyara.smartshop.service.impl;

import com.aliyara.smartshop.dto.request.OrderItemRequestDTO;
import com.aliyara.smartshop.dto.request.OrderRequestDTO;
import com.aliyara.smartshop.dto.response.OrderResponseDTO;
import com.aliyara.smartshop.exception.InsufficientStockException;
import com.aliyara.smartshop.exception.ResourceNotFoundException;
import com.aliyara.smartshop.mapper.OrderMapper;
import com.aliyara.smartshop.model.*;
import com.aliyara.smartshop.payload.ApiResponse;
import com.aliyara.smartshop.repository.ClientRepository;
import com.aliyara.smartshop.repository.OrderRepository;
import com.aliyara.smartshop.repository.ProductRepository;
import com.aliyara.smartshop.repository.PromoCodeRepository;
import com.aliyara.smartshop.service.interfaces.DiscountService;
import com.aliyara.smartshop.service.interfaces.OrderService;
import com.aliyara.smartshop.service.interfaces.PromoCodeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ClientRepository clientRepository;
    private final PromoCodeRepository promoCodeRepository;
    private final PromoCodeService promoCodeService;
    private final DiscountService discountService;
    private final ProductRepository productRepository;

    @Override
    public OrderResponseDTO create(OrderRequestDTO requestDTO) {
//        Order order = orderMapper.toEntity(requestDTO);
        Order order = new Order();
        Client client = clientRepository.findById(requestDTO.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found!"));
        order.setClient(client);
        double subtotal = 0.0;

        for (OrderItemRequestDTO orderItem : requestDTO.getOrderItems()) {
            Product product = productRepository.findById(orderItem.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product with ID" + orderItem.getProductId() + " not found!"));
            boolean available = checkIfStockAvailable(product, orderItem.getQuantity());
            if(available) {
                OrderItem item = new OrderItem();
                item.setQuantity(orderItem.getQuantity());
                item.setProduct(product);
                item.setOrder(order);
                item.setTotalLine(product.getUnitPrice() * orderItem.getQuantity());
                subtotal += item.getTotalLine();
                order.addItem(item);
            } else {
                throw new InsufficientStockException("Insufficient Stock for " + product.getName());
            }
        }

        order.setSubtotal(subtotal);

        if (requestDTO.getPromoCode() != null) {
            boolean isPromoCodeValid = promoCodeService.checkIfPromoCodeValid(requestDTO.getPromoCode());
            if (isPromoCodeValid) {
                PromoCode promoCode = promoCodeRepository.findByCode(requestDTO.getPromoCode()).get();
                order.setPromoCode(promoCode);
                double discount = discountService.calculateDiscount(order);
                order.setDiscount(discount);
            }
        }
        double VAT = (order.getSubtotal() - order.getDiscount()) / order.getVAT();
        double finalPrice = order.getSubtotal() - VAT;
        order.setTotal(finalPrice);

        Order savedOrder = orderRepository.save(order);
        return orderMapper.toResponse(savedOrder);

    }

    @Override
    public OrderResponseDTO update(OrderRequestDTO requestDTO, UUID id) {
        return null;
    }

    @Override
    public ApiResponse<Void> softDelete(UUID id) {
        return null;
    }

    @Override
    public OrderResponseDTO findById(UUID id) {
        return null;
    }

    @Override
    public List<OrderResponseDTO> getAllOrders() {
        return List.of();
    }

    public boolean checkIfStockAvailable(Product product, int quantity) {
        return product.getStock() >= quantity;
    }
}
