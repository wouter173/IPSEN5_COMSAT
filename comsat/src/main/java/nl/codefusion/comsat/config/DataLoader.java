package nl.codefusion.comsat.config;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.models.UserModel;
import nl.codefusion.comsat.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.findByUsername("john_doe").isEmpty()) {
            UserModel user = new UserModel();
            user.setUsername("john_doe");
            user.setPassword(passwordEncoder.encode("password"));
            user.setRoles(Collections.singleton("USER"));
            this.userRepository.save(user);
        }
    }
}
