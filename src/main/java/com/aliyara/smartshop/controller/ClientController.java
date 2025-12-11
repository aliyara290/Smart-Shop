package com.aliyara.smartshop.controller;

import com.aliyara.smartshop.dto.request.ClientRequestDTO;
import com.aliyara.smartshop.dto.response.ClientProfileResponseDTO;
import com.aliyara.smartshop.dto.response.ClientResponseDTO;
import com.aliyara.smartshop.dto.response.OrderResponseDTO;
import com.aliyara.smartshop.payload.ApiResponse;
import com.aliyara.smartshop.service.interfaces.ClientService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ClientController {
    private final ClientService clientService;

    @PostMapping("/admin/clients")
    public ResponseEntity<ApiResponse<ClientResponseDTO>> createClient(@Valid @RequestBody ClientRequestDTO requestDTO) {
        ClientResponseDTO savedClient = clientService.create(requestDTO);
        ApiResponse<ClientResponseDTO> response = new ApiResponse<>(true, "Client created successfully", LocalDateTime.now(), savedClient);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/admin/clients/{id}")
    public ResponseEntity<ApiResponse<ClientResponseDTO>> updateClient(@Valid @RequestBody ClientRequestDTO requestDTO, @PathVariable UUID id) {
        ClientResponseDTO updatedClient = clientService.update(requestDTO, id);
        ApiResponse<ClientResponseDTO> response = new ApiResponse<>(true, "Client updated successfully", LocalDateTime.now(), updatedClient);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/admin/clients/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteClient(@PathVariable UUID id) {
        ApiResponse<Void> deletedClient = clientService.softDelete(id);
        return ResponseEntity.ok().body(deletedClient);
    }

    @GetMapping("/admin/clients/{id}")
    public ResponseEntity<ApiResponse<ClientResponseDTO>> getClient(@PathVariable UUID id) {
        ClientResponseDTO client = clientService.findById(id);
        ApiResponse<ClientResponseDTO> response = new ApiResponse<>(true, "Client fetched successfully!", LocalDateTime.now(), client);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/admin/clients")
    public ResponseEntity<ApiResponse<List<ClientResponseDTO>>> getAllClients() {
        List<ClientResponseDTO> clients = clientService.getAllClients();
        ApiResponse<List<ClientResponseDTO>> response = new ApiResponse<>(true, "Clients fetched successfully!", LocalDateTime.now(), clients);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/account")
    public ResponseEntity<ApiResponse<ClientProfileResponseDTO>> getProfile(HttpSession session) {
        ClientProfileResponseDTO client = clientService.getProfile(session);
        ApiResponse<ClientProfileResponseDTO> response = new ApiResponse<>(true, "Client profile fetch with success", LocalDateTime.now(), client);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/account/client-orders")
    public ResponseEntity<ApiResponse<List<OrderResponseDTO>>> getClientOrders(HttpSession session) {
        List<OrderResponseDTO> orders = clientService.getClientOrders(session);
        ApiResponse<List<OrderResponseDTO>> response = new ApiResponse<>(true, "Client orders fetch with success", LocalDateTime.now(), orders);
        return ResponseEntity.ok().body(response);
    }
}
