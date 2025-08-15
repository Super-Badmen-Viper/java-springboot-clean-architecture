package com.simple.bootstrap.db;

import com.simple.domain.auth.model.User;
import com.simple.repository.auth.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    private final UserRepository userRepository;

    public DataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            logger.info("No users found in the database. Creating default admin user...");
            User adminUser = new User();
            adminUser.setEmail("admin@gmail.com");
            // This hash corresponds to the raw password "admin123"
            adminUser.setPassword("$2a$12$6qC/qVwV3LINy.Z2hMDyw.gmnc24JWSluOWjytze1AxwC//ttZv86");
            adminUser.setUserName("admin");
            adminUser.setRole("ADMIN");
            adminUser.setCreateTime(new java.util.Date());
            adminUser.setUpdateTime(new java.util.Date());

            userRepository.save(adminUser);
            logger.info("Default admin user created successfully.");
        } else {
            logger.info("User data already exists. Skipping data initialization.");
        }
    }
}
