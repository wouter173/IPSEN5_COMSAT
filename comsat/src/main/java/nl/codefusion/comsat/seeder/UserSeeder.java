package nl.codefusion.comsat.seeder;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.models.Role;
import nl.codefusion.comsat.config.PermissionConfig;
import nl.codefusion.comsat.models.UserModel;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@RequiredArgsConstructor
@Component
public class UserSeeder {
    private final PasswordEncoder passwordEncoder;

    public void seedUsers() {
        Role adminRole = seedRoles("admin", PermissionConfig.MANAGE_USERS);
        UserModel adminUser = seed("admin", "admin", adminRole);
    }

    private UserModel seed(String username, String password, Role role) {
        return UserModel.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(role)
                .build();
    }

    private Role seedRoles(String name, int permissions){
        return new Role(name, permissions);
    }
}