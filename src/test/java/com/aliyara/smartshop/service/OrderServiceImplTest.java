package com.aliyara.smartshop.service.impl;

import com.aliyara.smartshop.dto.request.OrderItemRequestDTO;
import com.aliyara.smartshop.dto.request.OrderRequestDTO;
import com.aliyara.smartshop.dto.response.OrderResponseDTO;
import com.aliyara.smartshop.enums.ClientLoyaltyLevel;
import com.aliyara.smartshop.enums.OrderStatus;
import com.aliyara.smartshop.exception.InsufficientStockException;
import com.aliyara.smartshop.exception.ResourceNotFoundException;
import com.aliyara.smartshop.mapper.OrderMapper;
import com.aliyara.smartshop.model.*;
import com.aliyara.smartshop.repository.ClientRepository;
import com.aliyara.smartshop.repository.OrderRepository;
import com.aliyara.smartshop.repository.ProductRepository;
import com.aliyara.smartshop.service.interfaces.ClientService;
import com.aliyara.smartshop.service.interfaces.DiscountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private DiscountService discountService;

    @Mock
    private ClientService clientService;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Client testClient;
    private Product testProduct;
    private Order testOrder;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(orderService, "vat", 20.0);

        testClient = new Client();
        testClient.setId(UUID.randomUUID());
        testClient.setLoyaltyLevel(ClientLoyaltyLevel.BASIC);

        testProduct = new Product();
        testProduct.setId(UUID.randomUUID());
        testProduct.setName("Test Product");
        testProduct.setUnitPrice(100.0);
        testProduct.setStock(10);

        testOrder = new Order();
        testOrder.setId(UUID.randomUUID());
        testOrder.setClient(testClient);
        testOrder.setSubtotal(200.0);
        testOrder.setTotal(240.0);
        testOrder.setStatus(OrderStatus.PENDING);
        testOrder.setOrderItems(new ArrayList<>());
    }

    @Test
    void createOrder_Success() {
        OrderItemRequestDTO itemDTO = new OrderItemRequestDTO();
        itemDTO.setProductId(testProduct.getId());
        itemDTO.setQuantity(2);

        OrderRequestDTO requestDTO = new OrderRequestDTO();
        requestDTO.setClientId(testClient.getId());
        requestDTO.setOrderItems(List.of(itemDTO));

        when(clientRepository.findById(any())).thenReturn(Optional.of(testClient));
        when(productRepository.findById(any())).thenReturn(Optional.of(testProduct));
        when(discountService.calculateDiscount(any())).thenReturn(0.0);
        when(orderRepository.save(any())).thenReturn(testOrder);
        when(orderMapper.toResponse(any())).thenReturn(
                OrderResponseDTO.builder().id(testOrder.getId()).build()
        );

        OrderResponseDTO result = orderService.create(requestDTO);

        assertNotNull(result);
        verify(orderRepository, times(1)).save(any());
    }

    @Test
    void findById_Success() {

        UUID orderId = UUID.randomUUID();
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(testOrder));
        when(orderMapper.toResponse(testOrder)).thenReturn(
                OrderResponseDTO.builder().id(orderId).build()
        );

        OrderResponseDTO result = orderService.findById(orderId);

        assertNotNull(result);
        assertEquals(orderId, result.id());
    }

    @Test
    void findById_NotFound_ThrowsException() {
        UUID orderId = UUID.randomUUID();
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.findById(orderId));
    }

    @Test
    void getAllOrders_Success() {
        when(orderRepository.findAll()).thenReturn(List.of(testOrder));
        when(orderMapper.toResponse(any())).thenReturn(
                OrderResponseDTO.builder().id(testOrder.getId()).build()
        );

        List<OrderResponseDTO> result = orderService.getAllOrders();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void softDelete_Success() {
        UUID orderId = UUID.randomUUID();
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(testOrder));

        orderService.softDelete(orderId);

        verify(orderRepository, times(1)).delete(testOrder);
    }
}