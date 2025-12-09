package com.aliyara.smartshop.service.impl;

import com.aliyara.smartshop.dto.request.ClientRequestDTO;
import com.aliyara.smartshop.dto.response.ClientResponseDTO;
import com.aliyara.smartshop.enums.OrderStatus;
import com.aliyara.smartshop.exception.DuplicateResourceException;
import com.aliyara.smartshop.exception.ResourceNotFoundException;
import com.aliyara.smartshop.mapper.ClientMapper;
import com.aliyara.smartshop.mapper.UserMapper;
import com.aliyara.smartshop.model.Client;
import com.aliyara.smartshop.model.User;
import com.aliyara.smartshop.enums.ClientLoyaltyLevel;
import com.aliyara.smartshop.enums.UserRole;
import com.aliyara.smartshop.payload.ApiResponse;
import com.aliyara.smartshop.repository.ClientRepository;
import com.aliyara.smartshop.repository.OrderRepository;
import com.aliyara.smartshop.repository.UserRepository;
import com.aliyara.smartshop.service.interfaces.ClientService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ClientMapper clientMapper;

    @Override
    public ClientResponseDTO create(ClientRequestDTO requestDTO) {
        validateEmailUniqueness(requestDTO.getUser().getEmail());

        User user = userMapper.toEntity(requestDTO.getUser());
        user.setLastName("salma");
        user.setPassword(BCrypt.hashpw(requestDTO.getUser().getPassword(), BCrypt.gensalt()));
        user.setRole(UserRole.valueOf(requestDTO.getUser().getRole().toUpperCase()));
        Client client = clientMapper.toEntity(requestDTO);
        client.setLoyaltyLevel(ClientLoyaltyLevel.BASIC);
        client.setUser(user);
        Client savedClient = clientRepository.save(client);
        return clientMapper.toResponse(savedClient);
    }

    @Override
    public ClientResponseDTO update(ClientRequestDTO requestDTO, UUID id) {
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
    public ApiResponse<Void> softDelete(UUID id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client with ID " + id + " not exist!"));
        clientRepository.delete(client);
        return new ApiResponse<>(true, "User Deleted successfully!", LocalDateTime.now(), null);
    }

    @Override
    public ClientResponseDTO findById(UUID id) {
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

    @Override
    public void updateClientLoyaltyLevel(Client client) {
        long clientOrdersCount = orderRepository.countByClientIdAndStatus(client.getId(), OrderStatus.CONFIRMED);
        Double clientCumulative = orderRepository.getConfirmedTotal(client.getId());

        double total = clientCumulative != null ? clientCumulative : 0.0;

        if (clientOrdersCount >= 20 || total >= 15000.0) {
            log.debug("20 order");
            client.setLoyaltyLevel(ClientLoyaltyLevel.PLATINUM);
        } else if (clientOrdersCount >= 10 || total >= 5000.0) {
            log.debug("10 order");
            client.setLoyaltyLevel(ClientLoyaltyLevel.GOLD);
        } else if (clientOrdersCount >= 3 || total >= 1000.0) {
            log.debug("3 order");
            client.setLoyaltyLevel(ClientLoyaltyLevel.SILVER);
        } else {
            log.debug("0 order");
            client.setLoyaltyLevel(ClientLoyaltyLevel.BASIC);
        }

        log.debug("hello im here");
        clientRepository.save(client);
    }
}
