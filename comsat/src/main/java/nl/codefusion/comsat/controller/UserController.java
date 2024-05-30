package nl.codefusion.comsat.controller;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.config.Permission;
import nl.codefusion.comsat.dao.UserDao;
import nl.codefusion.comsat.dto.UserResponseDto;
import nl.codefusion.comsat.models.UserModel;
import nl.codefusion.comsat.service.PermissionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.NoPermissionException;


@RestController
@RequestMapping(value = "/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final PermissionService permissionService;
    private final UserDao userDao;

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getMe() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        // Not sure how to throw a 404 error
        UserModel user = userDao.findByUsername(name).orElse(null);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        UserResponseDto result = UserResponseDto.builder()
                .mfaEnabled(user.isMfaEnabled())
                .role(user.getRoleModel().getName())
                .username(user.getUsername())
                .build();

        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<String> getUsers() throws NoPermissionException {
        if (permissionService.hasPermission(permissionService.getPrincipalRoles(), Permission.READ_USER)) {
            return ResponseEntity.ok("Users");
        }
        throw new NoPermissionException();
    }
}



