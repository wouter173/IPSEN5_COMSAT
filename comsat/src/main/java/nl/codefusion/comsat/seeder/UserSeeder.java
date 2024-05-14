package nl.codefusion.comsat.seeder;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.models.Role;
import nl.codefusion.comsat.config.Permission;
import nl.codefusion.comsat.models.UserModel;
import nl.codefusion.comsat.repository.RoleRepository;
import nl.codefusion.comsat.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserSeeder {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public void seedUsers() {
        Role adminRole = seedRoles("admin", Permission.MANAGE_USERS.getValue());
        Role researcherRole = seedRoles("admin", Permission.EDIT_TEMPLATE.getValue());
        UserModel adminUser = seed("admin@gmail.com", "admin", adminRole);
        UserModel researcherUser = seed("researcher@gmail.com", "researcher", researcherRole);
        roleRepository.save(adminRole);
        roleRepository.save(researcherRole);

        userRepository.save(adminUser);
        userRepository.save(researcherUser);
    }

    private UserModel seed(String username, String password, Role role) {
        return UserModel.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(role)
                .build();
    }

    private Role seedRoles(String name, int permissions) {
        return Role.builder()
                .name(name)
                .permissions(permissions)
                .build();
    }
}