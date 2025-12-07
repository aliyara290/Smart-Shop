package com.aliyara.smartshop.config;

import com.aliyara.smartshop.enums.UserRole;
import com.aliyara.smartshop.model.User;
import com.aliyara.smartshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {

        boolean adminExistence = userRepository.existsByEmail("admin@smartshop.com");

        if(!adminExistence) {
            User admin = new User();
            admin.setEmail("admin@smartshop.com");
            admin.setFirstName("Admin");
            admin.setLastName("Smart");
            admin.setPassword(BCrypt.hashpw("admin1234", BCrypt.gensalt()));
            admin.setRole(UserRole.ADMIN);

            userRepository.save(admin);
        }
    }
}
