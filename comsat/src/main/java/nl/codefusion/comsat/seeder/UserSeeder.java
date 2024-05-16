package nl.codefusion.comsat.seeder;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.dao.RoleDao;
import nl.codefusion.comsat.dao.UserDao;
import nl.codefusion.comsat.models.RoleModel;
import nl.codefusion.comsat.config.Permission;
import nl.codefusion.comsat.models.UserModel;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserSeeder {
    private final PasswordEncoder passwordEncoder;
    private final UserDao userDao;
    private final RoleDao roleDao;

    public void seedUsers() {
        RoleModel adminRole = seedRole("admin", Permission.READ_USER.getValue());
        RoleModel researcherRole = seedRole("researcher", Permission.UPDATE_USER.getValue());

        seedUser("admin@gmail.com", "admin", adminRole );
        seedUser("researcher@gmail.com", "researcher", researcherRole);
    }

    @SuppressWarnings("UnusedReturnValue")
    private UserModel seedUser(String username, String password, RoleModel roleModel) {
        UserModel user = UserModel.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .roleModel(roleModel)
                .build();
        return userDao.create(user);
    }

    private RoleModel seedRole(String name, int permissions) {
        RoleModel roleModel = RoleModel.builder()
                .name(name)
                .permissions(permissions)
                .build();
        return roleDao.create(roleModel);
    }
}