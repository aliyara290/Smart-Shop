package com.aliyara.smartshop.service.interfaces;

import com.aliyara.smartshop.dto.request.ClientRequestDTO;
import com.aliyara.smartshop.dto.request.OrderRequestDTO;
import com.aliyara.smartshop.dto.response.ClientResponseDTO;
import com.aliyara.smartshop.dto.response.OrderResponseDTO;
import com.aliyara.smartshop.payload.ApiResponse;

import java.util.List;

public interface ClientService {
    ClientResponseDTO create(ClientRequestDTO requestDTO);
    ClientResponseDTO update(ClientRequestDTO requestDTO, String id);
    ApiResponse<Void> delete(String id);
    ClientResponseDTO findById(String id);
    List<ClientResponseDTO> getAllClients();
}