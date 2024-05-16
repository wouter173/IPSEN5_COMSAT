package nl.codefusion.comsat.service;

import nl.codefusion.comsat.config.Permission;
import nl.codefusion.comsat.models.RoleModel;
import nl.codefusion.comsat.models.UserModel;
import nl.codefusion.comsat.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PermissionServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    PermissionService permissionService;

    @SuppressWarnings("resource")
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void hasPermission() {
        RoleModel roleModel = new RoleModel();
        roleModel.setPermissions(Permission.DELETE_USER.getValue());

        assertTrue(permissionService.hasPermission(roleModel, Permission.DELETE_USER));
    }
    @Test
    void should_fail_with_wrong_permission(){
        RoleModel roleModel = new RoleModel();
        roleModel.setPermissions(Permission.DELETE_USER.getValue());

        assertFalse(permissionService.hasPermission(roleModel, Permission.READ_USER));
    }
    @Test
    void getPrincipalRoles() {
        RoleModel roleModel = new RoleModel();
        UserModel user = new UserModel();
        user.setRoleModel(roleModel);
        when(userRepository.findRoleByUsername("username")).thenReturn(user);

        Authentication authentication = Mockito.mock(Authentication.class);
        when(authentication.getName()).thenReturn("username");

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);

        assertEquals(roleModel, permissionService.getPrincipalRoles());
    }
}