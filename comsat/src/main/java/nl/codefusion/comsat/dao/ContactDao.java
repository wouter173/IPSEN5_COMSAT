package nl.codefusion.comsat.dao;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.models.ContactModel;
import nl.codefusion.comsat.repository.ContactRepository;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public List<ContactModel> getAllContacts() {
        return contactRepository.findAll();
    }
}
