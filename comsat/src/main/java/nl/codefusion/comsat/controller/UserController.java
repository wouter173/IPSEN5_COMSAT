package nl.codefusion.comsat.controller;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.config.Permission;

import nl.codefusion.comsat.service.PermissionService;
import org.springframework.http.ResponseEntity;

import nl.codefusion.comsat.repository.RoleRepository;
import nl.codefusion.comsat.repository.UserRepository;
import nl.codefusion.comsat.service.PermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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


    @GetMapping
    public ResponseEntity<String> getUsers() throws NoPermissionException {
        if (permissionService.hasPermission(permissionService.getPrincipalRoles(), Permission.READ_USER)) {
            return ResponseEntity.ok("Users");
        }
        throw new NoPermissionException();
    }
}



