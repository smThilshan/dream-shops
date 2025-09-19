package com.thilshan.dream_shops.data;

import com.thilshan.dream_shops.model.Role;
import com.thilshan.dream_shops.model.User;
import com.thilshan.dream_shops.service.repository.RoleRepository;
import com.thilshan.dream_shops.service.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Transactional
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<String> defaultRoles = Set.of("ROLE_ADMIN", "ROLE_USER");
      createDefaultUserIfNotExist();
      createDefaultRoleIfNotExist(defaultRoles);
      createDefaultAdminIfNotExist();
    }

    private void createDefaultUserIfNotExist() {
        Role userRole = roleRepository.findByRoleName("ROLE_USER").get();

        for (int i = 1; i <= 5; i++) {
            String defaultEmail = "user" + i + "@example.com";
            if (userRepository.existsByEmail(defaultEmail)) {

                continue;
            }
                User user = new User();
                user.setFirstName("The User");
                user.setLastName("User" + i);
                user.setEmail(defaultEmail);
                user.setPassword(passwordEncoder.encode("123456")); // In a real application, ensure to hash passwords
                user.setRoles(Set.of(userRole));
                userRepository.save(user);
                System.out.println("Created default user: " + defaultEmail);
        }
    }

    private void createDefaultRoleIfNotExist(Set<String> roles){
        roles.stream()
                .filter(role -> roleRepository.findByRoleName(role).isEmpty())
                .map(Role::new).forEach(roleRepository::save);
    }

    private void createDefaultAdminIfNotExist() {
        Role adminRole = roleRepository.findByRoleName("ROLE_ADMIN").get();

        for (int i = 1; i <= 2; i++) {
            String defaultEmail = "user" + i + "@example.com";
            if (userRepository.existsByEmail(defaultEmail)) {

                continue;
            }
            User user = new User();
            user.setFirstName("Admin");
            user.setLastName("Admin" + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456")); // In a real application, ensure to hash passwords
            user.setRoles(Set.of(adminRole));
            userRepository.save(user);
            System.out.println("Created default Admin user: " + defaultEmail);
        }
    }
}
