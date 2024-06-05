package nl.codefusion.comsat.service;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.dao.ContactDao;
import nl.codefusion.comsat.dto.ContactDto;
import nl.codefusion.comsat.models.ContactModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactDao contactDao;

    public ResponseEntity<ContactModel> updateContact(UUID id, ContactModel contactDetails) {
        ContactModel updatedContact = contactDao.updateContact(id, contactDetails);
        return ResponseEntity.ok(updatedContact);
    }

    public ContactModel convertDtoToModel(ContactDto contactDto) {
    return ContactModel.builder()
            .firstName(contactDto.getFirstName())
            .nickname(contactDto.getNickname())
            .platform(contactDto.getPlatform())
            .audience(contactDto.getAudience())
            .sex(contactDto.getSex())
            .language(contactDto.getLanguage())
            .region(contactDto.getRegion())
            .status(contactDto.getStatus())
            .build();
}
}