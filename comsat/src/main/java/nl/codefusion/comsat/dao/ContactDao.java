package nl.codefusion.comsat.dao;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.models.ContactModel;
import nl.codefusion.comsat.repository.ContactRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContactDao {
    private final ContactRepository contactRepository;

    public ContactModel create(ContactModel contactModel) {return contactRepository.save(contactModel);}
}
