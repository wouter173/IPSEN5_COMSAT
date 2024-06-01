package nl.codefusion.comsat.service;

import nl.codefusion.comsat.models.ContactModel;
import nl.codefusion.comsat.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public ResponseEntity<ContactModel> updateContact(UUID id, ContactModel contactDetails) {
        ContactModel updatedContact = contactRepository.findById(id).map(contact -> {
            Optional.ofNullable(contactDetails.getFirstName()).ifPresent(contact::setFirstName);
            Optional.ofNullable(contactDetails.getNickname()).ifPresent(contact::setNickname);
            Optional.ofNullable(contactDetails.getAudience()).ifPresent(contact::setAudience);
            Optional.ofNullable(contactDetails.getSex()).ifPresent(contact::setSex);
            return contactRepository.save(contact);
        }).orElseThrow();
        return ResponseEntity.ok(updatedContact);
    }
}
