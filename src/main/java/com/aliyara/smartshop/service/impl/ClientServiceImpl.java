package com.aliyara.smartshop.service.impl;

import com.aliyara.smartshop.dto.request.ClientRequestDTO;
import com.aliyara.smartshop.dto.response.ClientResponseDTO;
import com.aliyara.smartshop.exception.DuplicateResourceException;
import com.aliyara.smartshop.exception.ResourceNotFoundException;
import com.aliyara.smartshop.mapper.ClientMapper;
import com.aliyara.smartshop.mapper.UserMapper;
import com.aliyara.smartshop.model.Client;
import com.aliyara.smartshop.model.User;
import com.aliyara.smartshop.model.enums.ClientLoyaltyLevel;
import com.aliyara.smartshop.model.enums.UserRole;
import com.aliyara.smartshop.payload.ApiResponse;
import com.aliyara.smartshop.repository.ClientRepository;
import com.aliyara.smartshop.repository.UserRepository;
import com.aliyara.smartshop.service.interfaces.ClientService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ClientMapper clientMapper;

    @Override
    public ClientResponseDTO create(ClientRequestDTO requestDTO) {
        validateEmailUniqueness(requestDTO.getUser().getEmail());

        User user = userMapper.toEntity(requestDTO.getUser());
        user.setPassword(BCrypt.hashpw(requestDTO.getUser().getPassword(), BCrypt.gensalt()));
        user.setRole(UserRole.valueOf(requestDTO.getUser().getRole().toUpperCase()));
        Client client = clientMapper.toEntity(requestDTO);
        client.setLoyaltyLevel(ClientLoyaltyLevel.BASIC);
        client.setUser(user);
        Client savedClient = clientRepository.save(client);
        return clientMapper.toResponse(savedClient);
    }

    @Override
    public ClientResponseDTO update(ClientRequestDTO requestDTO, String id) {
        Client exisitngClient = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " not exist!"));

        clientMapper.updateClientFromDTO(requestDTO, exisitngClient);
        if(requestDTO.getUser().getPassword() != null) {
            exisitngClient.getUser().setPassword(BCrypt.hashpw(requestDTO.getUser().getPassword(), BCrypt.gensalt()));
        }

        Client updatedClient = clientRepository.save(exisitngClient);
        return clientMapper.toResponse(updatedClient);
    }

    @Override
    public ApiResponse<Void> softDelete(String id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client with ID " + id + " not exist!"));
        if(!client.getUser().isDeleted()) {
            client.getUser().setDeleted(true);
            client.getUser().setDeletedAt(LocalDateTime.now());
        }
        return new ApiResponse<>(true, "User Deleted successfully!", LocalDateTime.now(), null);
    }

    @Override
    public ClientResponseDTO findById(String id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client with ID " + id + " not exist!"));
        return clientMapper.toResponse(client);
    }

    @Override
    public List<ClientResponseDTO> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream().map(clientMapper::toResponse).toList();
    }

    public void validateEmailUniqueness(String email) {
        if(userRepository.existsByEmail(email)) {
            throw new DuplicateResourceException("User with email " + email + " already exist! use another email address");
        }
    }
}
