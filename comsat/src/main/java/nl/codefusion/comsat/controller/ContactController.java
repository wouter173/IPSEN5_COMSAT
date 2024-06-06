package nl.codefusion.comsat.controller;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.config.Permission;
import nl.codefusion.comsat.dao.ContactDao;
import nl.codefusion.comsat.dto.ContactDto;
import nl.codefusion.comsat.models.ContactModel;
import nl.codefusion.comsat.repository.ContactRepository;
import nl.codefusion.comsat.service.ContactService;
import nl.codefusion.comsat.service.PermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.naming.NoPermissionException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/contacts")
public class ContactController {

    private final ContactRepository contactRepository;

    private final PermissionService permissionService;
    private final ContactDao contactDao;

    private final ContactService contactService;

    @PostMapping()
    public ContactModel createContact(@Validated @RequestBody ContactDto contactDto) throws NoPermissionException {
        if (permissionService.hasPermission(permissionService.getPrincipalRoles(), Permission.CREATE_CONTACT)) {
            ContactModel contactModel = contactService.convertDtoToModel(contactDto);
            return contactRepository.save(contactModel);
        }
        throw new NoPermissionException();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable UUID id) throws NoPermissionException {
        if (permissionService.hasPermission(permissionService.getPrincipalRoles(), Permission.READ_CONTACT_DETAILS)) {
            contactRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        throw new NoPermissionException();

    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactModel> updateContact(@PathVariable UUID id, @Validated @RequestBody ContactDto contactDto) throws NoPermissionException {
        if (permissionService.hasPermission(permissionService.getPrincipalRoles(), Permission.UPDATE_CONTACT)) {
            ContactModel contactModel = contactService.convertDtoToModel(contactDto);
            return contactService.updateContact(id, contactModel);
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