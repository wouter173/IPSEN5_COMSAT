package nl.codefusion.comsat.service;

import nl.codefusion.comsat.config.Permission;
import nl.codefusion.comsat.models.Role;

public class PermissionService {
    public boolean hasPermission(Role role, Permission neededPermission){
        return (role.getPermissions() & neededPermission.getValue()) == neededPermission.getValue();
    }
}
