package nl.codefusion.comsat.service;

import lombok.AllArgsConstructor;
import nl.codefusion.comsat.config.Permission;
import nl.codefusion.comsat.models.RoleModel;
import nl.codefusion.comsat.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class PermissionService {
    private final UserRepository userRepository;

    public boolean hasPermission(RoleModel roleModel, Permission neededPermission){
        return (roleModel.getPermissions() & neededPermission.getValue()) == neededPermission.getValue();
    }

    public RoleModel getPrincipalRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return userRepository.findRoleByUsername(currentPrincipalName).getRoleModel();
    }

    public int sumPermission(Permission... permissions) {
        return Arrays.stream(permissions).mapToInt(Permission::getValue).distinct().sum();
    }
}
