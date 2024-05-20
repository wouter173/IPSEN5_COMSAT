package nl.codefusion.comsat.controller;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.config.Permission;
import nl.codefusion.comsat.dao.ContactDao;
import nl.codefusion.comsat.models.ContactModel;
import nl.codefusion.comsat.service.ContactService;
import nl.codefusion.comsat.service.PermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.NoPermissionException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/contacts")
@RequiredArgsConstructor
public class ContactController {
    private final PermissionService permissionService;
    private final ContactDao contactService;
    @GetMapping
    public ResponseEntity<List<ContactModel>> getContacts() throws NoPermissionException{
        if (permissionService.hasPermission(permissionService.getPrincipalRoles(), Permission.READ_CONTACT_DETAILS)) {
            return ResponseEntity.ok(contactService.getAllContacts());
        }
        throw new NoPermissionException();

    }

}
