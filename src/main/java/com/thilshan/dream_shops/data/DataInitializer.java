package com.thilshan.dream_shops.data;

import com.thilshan.dream_shops.model.User;
import com.thilshan.dream_shops.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final UserRepository userRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
      createDefaultUserIfNotExist();
    }

    private void createDefaultUserIfNotExist() {
        for (int i = 1; i <= 5; i++) {
            String defaultEmail = "user" + i + "@example.com";
            if (userRepository.existsByEmail(defaultEmail)) {

                continue;
            }
                User user = new User();
                user.setFirstName("The User");
                user.setLastName("User" + i);
                user.setEmail(defaultEmail);
                user.setPassword("12345"); // In a real application, ensure to hash passwords
                userRepository.save(user);
                System.out.println("Created default user: " + defaultEmail);
        }
    }
}
