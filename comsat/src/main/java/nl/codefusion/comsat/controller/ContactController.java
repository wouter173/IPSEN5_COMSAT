package nl.codefusion.comsat.controller;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.config.Permission;
import nl.codefusion.comsat.dao.ContactDao;
import nl.codefusion.comsat.models.ContactModel;
import nl.codefusion.comsat.repository.ContactRepository;
import nl.codefusion.comsat.service.ContactService;
import nl.codefusion.comsat.service.PermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.NoPermissionException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ContactController {

    private final ContactRepository contactRepository;

    private final PermissionService permissionService;

    private final ContactDao contactDao;

    private final ContactService contactService;

    @PostMapping("/contacts")
    public ContactModel createContact(@RequestBody ContactModel contact) throws NoPermissionException {
        if (permissionService.hasPermission(permissionService.getPrincipalRoles(), Permission.CREATE_CONTACT)) {
            return contactRepository.save(contact);
        }
        throw new NoPermissionException();
    }

    @DeleteMapping("/contacts/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable UUID id) throws NoPermissionException {
        if (permissionService.hasPermission(permissionService.getPrincipalRoles(), Permission.READ_CONTACT_DETAILS)) {
            contactRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        throw new NoPermissionException();

    }

    @PutMapping("/contacts/{id}")
    public ResponseEntity<ContactModel> updateContact(@PathVariable UUID id, @RequestBody ContactModel contactDetails) throws NoPermissionException {
        if (permissionService.hasPermission(permissionService.getPrincipalRoles(), Permission.UPDATE_CONTACT)) {
            return contactService.updateContact(id, contactDetails);
        }
        throw new NoPermissionException();
    }

    @GetMapping
    public ResponseEntity<List<ContactModel>> getContacts() throws NoPermissionException {
        if (permissionService.hasPermission(permissionService.getPrincipalRoles(), Permission.READ_CONTACT_DETAILS)) {
            return ResponseEntity.ok(contactDao.getAllContacts());
        }
        throw new NoPermissionException();

    }
}