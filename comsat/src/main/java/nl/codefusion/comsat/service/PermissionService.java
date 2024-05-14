package nl.codefusion.comsat.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nl.codefusion.comsat.config.Permission;
import nl.codefusion.comsat.models.Role;
import nl.codefusion.comsat.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PermissionService {
    private final UserRepository userRepository;

    public boolean hasPermission(Role role, Permission neededPermission){
        return (role.getPermissions() & neededPermission.getValue()) == neededPermission.getValue();
    }

    public Role getPrincipalRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return userRepository.findRoleByUsername(currentPrincipalName).getRole();
    }
}
