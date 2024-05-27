package nl.codefusion.comsat.controller;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.config.Permission;
import nl.codefusion.comsat.dao.ContactDao;
import nl.codefusion.comsat.models.ContactModel;
import nl.codefusion.comsat.repository.ContactRepository;
import nl.codefusion.comsat.service.PermissionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
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
    private final String kik_api = "http://127.0.0.1:5000";


    RestTemplate restTemplate = new RestTemplate();


    @PostMapping("/contacts")
    public ContactModel createContact(@RequestBody ContactModel contact) {
        return contactRepository.save(contact);
    }

    @Value(kik_api)
    private String engineUrl;

    @GetMapping
    public ResponseEntity<List<ContactModel>> getContacts() throws NoPermissionException {
        if (permissionService.hasPermission(permissionService.getPrincipalRoles(), Permission.READ_CONTACT_DETAILS)) {
            return ResponseEntity.ok(contactDao.getAllContacts());
        }
        throw new NoPermissionException();

    }

    @GetMapping(value = "/test")
    public ResponseEntity<Map<String, String>> getUserStatuses() {
        ResponseEntity<Map<String, String>> response = restTemplate.exchange(
                engineUrl + "/get_chat_status",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, String>>() {
                }
        );

        Map<String, String> statuses = response.getBody();
        if (statuses != null) {
            statuses.forEach((username, status) -> {
                try {
                    System.out.println("User: " + username + ", Status: " + status);
                    contactDao.updateBatchStatusByUsername(username, status);
                } catch (NullPointerException e) {
                    System.out.println("No contact found with username: " + username);
                }
            });
        }

        return response;
    }


}