package nl.codefusion.comsat.service;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.dao.BatchContactEntryDao;
import nl.codefusion.comsat.dao.ContactDao;
import nl.codefusion.comsat.dto.ContactDto;
import nl.codefusion.comsat.dto.ContactWithEntryResponseDto;
import nl.codefusion.comsat.dto.EntryResponseDto;
import nl.codefusion.comsat.models.BatchContactEntryModel;
import nl.codefusion.comsat.models.ContactModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactDao contactDao;
    private final PseudonymService pseudonymService;
    private final BatchContactEntryDao batchContactEntryDao;

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
                .build();
    }

    public void removeBatchFromContact(UUID contactId, UUID batchId) {
        ContactModel contactModel = contactDao.findById(contactId);
        BatchContactEntryModel batchContactEntryModel = batchContactEntryDao.findByBatchIdAndContactId(batchId, contactId);

        contactModel.removeBatchFromContact(batchContactEntryModel);

        contactDao.updateContact(contactId, contactModel);
        batchContactEntryDao.update(batchContactEntryModel.getId(), batchContactEntryModel);


    }

    public ContactWithEntryResponseDto convertModelToDto(ContactModel contactModel, boolean hasDetails) {
        String firstName;
        String nickname;

        List<EntryResponseDto> entries = contactModel.getBatchContacts().stream()
                .map(entry -> {
                    UUID batchId = null;
                    String batchName = null;
                    if (entry.getBatch() != null) {
                        batchId = entry.getBatch().getId();
                        batchName = entry.getBatch().getName();
                    }

                    return EntryResponseDto.builder()
                            .id(entry.getId())
                            .status(entry.getStatus())
                            .message(entry.getMessage())
                            .hidden(entry.isHidden())
                            .batchId(batchId)
                            .batchName(batchName)
                            .createdAt(entry.getCreatedAt())
                            .lastModified(entry.getLastModified())
                            .build();
                })
                .toList();

        if (hasDetails) {
            firstName = contactModel.getFirstName();
            nickname = contactModel.getNickname();
        } else {
            firstName = pseudonymService.generatePseudonym(contactModel.getNickname(), contactModel.getFirstName(), contactModel.getPlatform());
            nickname = "";
        }

        return ContactWithEntryResponseDto.builder()
                .id(contactModel.getId())
                .firstName(firstName)
                .nickname(nickname)
                .platform(contactModel.getPlatform())
                .audience(contactModel.getAudience())
                .sex(contactModel.getSex())
                .region(contactModel.getRegion())
                .language(contactModel.getLanguage())
                .entries(entries)
                .build();
    }

}