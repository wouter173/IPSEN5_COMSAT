package nl.codefusion.comsat.controller;

import nl.codefusion.comsat.models.ContactModel;
import nl.codefusion.comsat.repository.ContactRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ContactController {

    private final ContactRepository contactRepository;

    public ContactController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @PostMapping("/contacts")
    public ContactModel createContact(@RequestBody ContactModel contact) {
        return contactRepository.save(contact);
    }
}
