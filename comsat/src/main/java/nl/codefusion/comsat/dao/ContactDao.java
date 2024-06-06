package nl.codefusion.comsat.dao;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.models.ContactModel;
import nl.codefusion.comsat.repository.ContactRepository;
import org.springframework.stereotype.Component;

import java.util.List;
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

    public List<ContactModel> getAllContacts() {
        return contactRepository.findAll();
    }


    public ContactModel updateBatchStatusByUsername(String nickName, String status){
        ContactModel contact = contactRepository.findContactByNickname(nickName);
        contact.setStatus(status);
        return contactRepository.save(contact);
    }
}
