package com.aliyara.smartshop.controller;

import com.aliyara.smartshop.dto.request.ClientRequestDTO;
import com.aliyara.smartshop.dto.response.ClientResponseDTO;
import com.aliyara.smartshop.payload.ApiResponse;
import com.aliyara.smartshop.service.interfaces.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/clients")
public class ClientController {
    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<ApiResponse<ClientResponseDTO>> createClient(@Valid @RequestBody ClientRequestDTO requestDTO) {
        ClientResponseDTO savedClient = clientService.create(requestDTO);
        ApiResponse<ClientResponseDTO> response = new ApiResponse<>(true, "Client created successfully", LocalDateTime.now(), savedClient);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ClientResponseDTO>> updateClient(@Valid @RequestBody ClientRequestDTO requestDTO, @PathVariable String id) {
        ClientResponseDTO updatedClient = clientService.update(requestDTO, id);
        ApiResponse<ClientResponseDTO> response = new ApiResponse<>(true, "Client updated successfully", LocalDateTime.now(), updatedClient);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteClient(@PathVariable String id) {
        ApiResponse<Void> deletedClient = clientService.softDelete(id);
        return ResponseEntity.ok().body(deletedClient);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ClientResponseDTO>> getClient(@PathVariable String id) {
        ClientResponseDTO client = clientService.findById(id);
        ApiResponse<ClientResponseDTO> response = new ApiResponse<>(true, "Client fetched successfully!", LocalDateTime.now(), client);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ClientResponseDTO>>> getAllClients() {
        List<ClientResponseDTO> clients = clientService.getAllClients();
        ApiResponse<List<ClientResponseDTO>> response = new ApiResponse<>(true, "Clients fetched successfully!", LocalDateTime.now(), clients);
        return ResponseEntity.ok().body(response);
    }
}
