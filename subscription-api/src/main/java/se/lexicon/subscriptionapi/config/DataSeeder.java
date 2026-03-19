package se.lexicon.subscriptionapi.config;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import se.lexicon.subscriptionapi.domain.constant.UserCredentials;
import se.lexicon.subscriptionapi.domain.entity.User;
import se.lexicon.subscriptionapi.domain.entity.user.UserAdmin;
import se.lexicon.subscriptionapi.repository.UserRepository;


@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {
    private final UserRepository UserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        seedAdminUser();
        // seedRegularUser();
    }

    private void seedAdminUser() {
        String adminEmail = "admin@example.com";
        if (!UserRepository.existsByEmail(adminEmail)) {
            User admin = new UserAdmin();
            admin.setEmail(adminEmail);
            admin.setFirstName("Admin");
            admin.setLastName("Adminson");
            admin.setPassword(passwordEncoder.encode("password"));
            admin.setRoles(Set.of(UserCredentials.ROLE_ADMIN, UserCredentials.ROLE_USER));
            UserRepository.save(admin);
            log.info("[DATA_SEED] Admin user created: {}", adminEmail);
        }
    }

    // private void seedRegularUser() {
    //     String userEmail = "user@example.com";
    //     if (!UserRepository.existsByEmail(userEmail)) {
    //         User user = new User();
    //         user.setEmail(userEmail);
    //         user.setFirstName("User");
    //         user.setLastName("Userson");
    //         user.setPassword(passwordEncoder.encode("password"));
    //         user.setRoles(Set.of(Role.ROLE_USER));
    //         UserRepository.save(user);
    //         log.info("[DATA_SEED] Regular user created: {}", userEmail);
    //     }
    // }
}
