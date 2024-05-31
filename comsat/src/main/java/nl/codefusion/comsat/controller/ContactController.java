package nl.codefusion.comsat.controller;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.config.Permission;
import nl.codefusion.comsat.dao.ContactDao;
import nl.codefusion.comsat.models.ContactModel;
import nl.codefusion.comsat.repository.ContactRepository;
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

    @PostMapping("/contacts")
    public ContactModel createContact(@RequestBody ContactModel contact) {
        return contactRepository.save(contact);
    }

    @DeleteMapping("/contacts/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable UUID id) {
        contactRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/contacts/{id}")
    public ResponseEntity<ContactModel> updateContact(@PathVariable UUID id, @RequestBody ContactModel contactDetails) {
        ContactModel contact = contactRepository.findById(id).orElseThrow();

        if (contactDetails.getFirstName() != null) {
            contact.setFirstName(contactDetails.getFirstName());
        }
        if (contactDetails.getNickname() != null) {
            contact.setNickname(contactDetails.getNickname());
        }
        if (contactDetails.getAudience() != null) {
            contact.setAudience(contactDetails.getAudience());
        }
        if (contactDetails.getSex() != null) {
            contact.setSex(contactDetails.getSex());
        }
        ContactModel updatedContact = contactRepository.save(contact);
        return ResponseEntity.ok(updatedContact);
    }

    @GetMapping
    public ResponseEntity<List<ContactModel>> getContacts() throws NoPermissionException {
        if (permissionService.hasPermission(permissionService.getPrincipalRoles(), Permission.READ_CONTACT_DETAILS)) {
            return ResponseEntity.ok(contactDao.getAllContacts());
        }
        throw new NoPermissionException();

    }
}