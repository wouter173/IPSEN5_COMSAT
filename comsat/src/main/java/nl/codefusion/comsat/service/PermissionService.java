package nl.codefusion.comsat.service;

import nl.codefusion.comsat.models.Role;

public class PermissionService {
    public boolean hasPermission(Role role, int neededPermission){
        return(role.getPermissions() & neededPermission) == neededPermission;
    }
}
