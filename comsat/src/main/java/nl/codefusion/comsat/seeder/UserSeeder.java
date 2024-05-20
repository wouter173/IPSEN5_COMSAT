package nl.codefusion.comsat.seeder;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.dao.RoleDao;
import nl.codefusion.comsat.dao.UserDao;
import nl.codefusion.comsat.models.RoleModel;
import nl.codefusion.comsat.config.Permission;
import nl.codefusion.comsat.models.UserModel;
import nl.codefusion.comsat.service.PermissionService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@RequiredArgsConstructor
@Component
public class UserSeeder {
    private final PasswordEncoder passwordEncoder;
    private final UserDao userDao;
    private final RoleDao roleDao;
    private final PermissionService permissionService;

    public void seedUsers() {
        int adminSum = 0;
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


        for (Permission myEnum : Permission.values()) {
            adminSum += myEnum.getValue();
        }

        RoleModel adminRole = seedRole("admin", adminSum);
        RoleModel fictRole = seedRole("fict", fictPermissions);
        RoleModel researcherRole = seedRole("researcher", researcherPermissions);

        seedUser("admin@gmail.com", "admin", adminRole );
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