package nl.codefusion.comsat.seeder;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.config.Permission;
import nl.codefusion.comsat.dao.RoleDao;
import nl.codefusion.comsat.dao.UserDao;
import nl.codefusion.comsat.models.RoleModel;
import nl.codefusion.comsat.models.UserModel;
import nl.codefusion.comsat.service.PermissionService;
import org.slf4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserSeeder {
    private final PasswordEncoder passwordEncoder;
    private final UserDao userDao;
    private final RoleDao roleDao;
    private final PermissionService permissionService;
    private final Logger logger;

    public void seedUsers() {
        int fictPermissions = permissionService.sumPermission(
                Permission.CREATE_TEMPLATE,
                Permission.CREATE_BATCH,
                Permission.CREATE_CONTACT,
                Permission.READ_TEMPLATE,
                Permission.READ_BATCH,
                Permission.READ_CONTACT,
                Permission.UPDATE_TEMPLATE,
                Permission.UPDATE_BATCH,
                Permission.UPDATE_CONTACT,
                Permission.DELETE_TEMPLATE,
                Permission.DELETE_BATCH,
                Permission.DELETE_CONTACT,
                Permission.READ_USER,
                Permission.READ_CONTACT_DETAILS
        );

        int researcherPermissions = permissionService.sumPermission(
                Permission.SUGGEST_TEMPLATE,
                Permission.READ_TEMPLATE,
                Permission.READ_BATCH,
                Permission.READ_CONTACT
        );


        int adminSum = 0;
        for (Permission permission : Permission.values()) {
            adminSum += permission.getValue();
        }

        RoleModel adminRole = seedRole("admin", adminSum);
        RoleModel fictRole = seedRole("fict", fictPermissions);
        RoleModel researcherRole = seedRole("researcher", researcherPermissions);

        seedUser("admin@gmail.com", "admin", adminRole);
        seedUser("researcher@gmail.com", "researcher", researcherRole);
        seedUser("fict@gmail.com", "fict", fictRole);
    }

    @SuppressWarnings("UnusedReturnValue")
    private UserModel seedUser(String username, String password, RoleModel roleModel) {
        UserModel user = UserModel.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .roleModel(roleModel)
                .build();

        try {
            return userDao.create(user);
        } catch (Exception e) {
            logger.info("User {} already exists", username);
            return null;
        }
    }

    private RoleModel seedRole(String name, int permissions) {
        RoleModel roleModel = RoleModel.builder()
                .name(name)
                .permissions(permissions)
                .build();

        try {
            return roleDao.create(roleModel);
        } catch (Exception e) {
            logger.info("Role {} already exists", name);
            return null;
        }
    }
}