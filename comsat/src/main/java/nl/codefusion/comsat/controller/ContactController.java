package nl.codefusion.comsat.controller;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.config.Permission;
import nl.codefusion.comsat.dao.ContactDao;
import nl.codefusion.comsat.models.ContactModel;
import nl.codefusion.comsat.repository.ContactRepository;
import nl.codefusion.comsat.service.PermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.*;

import javax.naming.NoPermissionException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/contacts")
public class ContactController {

    private final ContactRepository contactRepository;
    private final PermissionService permissionService;
    private final ContactDao contactDao;

    @PostMapping("/contacts")
    public ContactModel createContact(@RequestBody ContactModel contact) {
        return contactRepository.save(contact);
    }


    @GetMapping
    public ResponseEntity<List<ContactModel>> getContacts() throws NoPermissionException {
        if (permissionService.hasPermission(permissionService.getPrincipalRoles(), Permission.READ_CONTACT_DETAILS)) {
            return ResponseEntity.ok(contactDao.getAllContacts());
        }
        throw new NoPermissionException();

    }

    @GetMapping("/platform-data")
    public List<Object[]> getPlatformData() {
        return contactRepository.findPlatfromData();
    }

    @GetMapping("/region-data")
    public List<Object[]> getRegionData() {
        return contactRepository.findRegionData();
    }

    @GetMapping("/gender-data")
    public List<Object[]> getGenderData() {
        return contactRepository.findGenderData();
    }

}