package nl.codefusion.comsat.controller;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.config.Permission;
import nl.codefusion.comsat.models.ContactModel;
import nl.codefusion.comsat.service.ContactService;
import nl.codefusion.comsat.service.PermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/contacts")
@RequiredArgsConstructor
public class ContactController {
    private final PermissionService permissionService;
    private final ContactService contactService;
    private boolean skipPermissions = true;

    @GetMapping//TODO reset the permission
    public ResponseEntity<List<ContactModel>> getContacts() {
        if (skipPermissions){
            return ResponseEntity.ok(contactService.getAllContacts());
        }
        return ResponseEntity.status(403).body(null);
    }

}
