package com.aliyara.smartshop.service.interfaces;

import com.aliyara.smartshop.dto.request.ClientRequestDTO;
import com.aliyara.smartshop.dto.response.ClientProfileResponseDTO;
import com.aliyara.smartshop.dto.response.ClientResponseDTO;
import com.aliyara.smartshop.dto.response.OrderResponseDTO;
import com.aliyara.smartshop.model.Client;
import com.aliyara.smartshop.payload.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.UUID;

public interface ClientService {
    ClientResponseDTO create(ClientRequestDTO requestDTO);
    ClientResponseDTO update(ClientRequestDTO requestDTO, UUID id);
    ApiResponse<Void> softDelete(UUID id);
    ClientResponseDTO findById(UUID id);
    List<ClientResponseDTO> getAllClients();
    void updateClientLoyaltyLevel(Client client);
    ClientProfileResponseDTO getProfile(HttpSession session);
    List<OrderResponseDTO> getClientOrders(HttpSession session);

}