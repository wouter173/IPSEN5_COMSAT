package nl.codefusion.comsat.dao;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.models.ContactModel;
import nl.codefusion.comsat.repository.ContactRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ContactDao {
    private final ContactRepository contactRepository;

    public ContactModel create(ContactModel contactModel) {
        return contactRepository.save(contactModel);
    }

    public ContactModel findByNickname(String nickname) {
        return contactRepository.findContactByNickname(nickname);
    }

    public ContactModel findById(UUID id) {
        return contactRepository.findById(id).orElse(null);
    }

    public List<ContactModel> getAllContacts() {
        return contactRepository.findAll();
    }

    public ContactModel updateContact(UUID id, ContactModel contactDetails) {
        return contactRepository.findById(id).map(contact -> {
            Optional.ofNullable(contactDetails.getFirstName()).ifPresent(contact::setFirstName);
            Optional.ofNullable(contactDetails.getNickname()).ifPresent(contact::setNickname);
            Optional.ofNullable(contactDetails.getAudience()).ifPresent(contact::setAudience);
            Optional.ofNullable(contactDetails.getSex()).ifPresent(contact::setSex);
            return contactRepository.save(contact);
        }).orElseThrow();
    }
}