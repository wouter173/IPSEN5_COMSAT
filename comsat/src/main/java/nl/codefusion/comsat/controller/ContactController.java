package nl.codefusion.comsat.controller;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.config.Permission;
import nl.codefusion.comsat.dao.ContactDao;
import nl.codefusion.comsat.dto.ContactDto;
import nl.codefusion.comsat.dto.ContactWithEntryResponseDto;
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
            return contactDao.create(contactModel);
        }
        throw new NoPermissionException();
    }

    @PutMapping("/{contactId}/batches/{batchId}/remove")
    public ResponseEntity<Void> removeBatchFromContact(@PathVariable UUID contactId, @PathVariable UUID batchId) throws NoPermissionException {
        if (!permissionService.hasPermission(permissionService.getPrincipalRoles(), Permission.UPDATE_CONTACT)) {
            throw new NoPermissionException();
        }
        contactService.removeBatchFromContact(contactId, batchId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable String id) throws NoPermissionException {
        if (permissionService.hasPermission(permissionService.getPrincipalRoles(), Permission.DELETE_CONTACT)) {
            ContactModel contact = contactDao.findById(UUID.fromString(id));
            contact.setDeleted(true);
            contactDao.updateContact(UUID.fromString(id), contact);

            return ResponseEntity.ok().build();
        }
        throw new NoPermissionException();

    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactModel> updateContact(@PathVariable String id, @Validated @RequestBody ContactDto contactDto) throws NoPermissionException {
        if (permissionService.hasPermission(permissionService.getPrincipalRoles(), Permission.UPDATE_CONTACT)) {
            ContactModel contactModel = contactService.convertDtoToModel(contactDto);
            return contactService.updateContact(UUID.fromString(id), contactModel);
        }
        throw new NoPermissionException();
    }

    @GetMapping
    public ResponseEntity<List<ContactWithEntryResponseDto>> getContacts() throws NoPermissionException {
        if (permissionService.hasPermission(permissionService.getPrincipalRoles(), Permission.READ_CONTACT_DETAILS)) {
            List<ContactModel> contacts = contactDao.getAllContacts().stream()
                    .filter(contact -> !contact.isDeleted())
                    .toList();

            List<ContactWithEntryResponseDto> contactWithEntryResponseDtos = contacts.stream()
                    .map(contact -> contactService.convertModelToDto(contact, true))
                    .toList();


            return ResponseEntity.ok(contactWithEntryResponseDtos);
        }
        else if (permissionService.hasPermission(permissionService.getPrincipalRoles(), Permission.READ_CONTACT)) {
            List<ContactModel> contacts = contactDao.getAllContacts().stream()
                    .filter(contact -> !contact.isDeleted())
                    .toList();

            List<ContactWithEntryResponseDto> contactWithEntryResponseDtos = contacts.stream()
                    .map(contact -> contactService.convertModelToDto(contact, false))
                    .toList();



            return ResponseEntity.ok(contactWithEntryResponseDtos);
        }
        throw new NoPermissionException();
    }


    @GetMapping("/platform-data")
    public List<Object[]> getPlatformData(@RequestParam(required = false) String batchId) {
        UUID batchUUID = batchId != null ? UUID.fromString(batchId) : null;
        return contactRepository.findPlatfromData(batchUUID);
    }

    @GetMapping("/region-data")
    public List<Object[]> getRegionData(@RequestParam(required = false) String batchId) {
        UUID batchUUID = batchId != null ? UUID.fromString(batchId) : null;
        return contactRepository.findRegionData(batchUUID);
    }

    @GetMapping("/gender-data")
    public List<Object[]> getGenderData(@RequestParam(required = false) String batchId) {
        UUID batchUUID = batchId != null ? UUID.fromString(batchId) : null;
        return contactRepository.findGenderData(batchUUID);
    }
}