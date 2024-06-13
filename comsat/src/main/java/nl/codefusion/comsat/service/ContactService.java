package nl.codefusion.comsat.service;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.dao.ContactDao;
import nl.codefusion.comsat.dto.ContactDto;
import nl.codefusion.comsat.dto.ContactWithEntryResponseDto;
import nl.codefusion.comsat.dto.EntryResponseDto;
import nl.codefusion.comsat.models.ContactModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
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
                .build();
    }

    public ContactWithEntryResponseDto convertModelToDto(ContactModel contactModel) {
        List<EntryResponseDto> entries = contactModel.getBatchContacts().stream()
                .map(entry -> EntryResponseDto.builder()
                        .id(entry.getId())
                        .status(entry.getStatus())
                        .message(entry.getMessage())
                        .hidden(entry.isHidden())
                        .batchId(entry.getBatch().getId())
                        .batchName(entry.getBatch().getName())
                        .createdAt(entry.getCreatedAt())
                        .lastModified(entry.getLastModified())
                        .build())
                .toList();

        return ContactWithEntryResponseDto.builder()
                .id(contactModel.getId())
                .firstName(contactModel.getFirstName())
                .nickname(contactModel.getNickname())
                .platform(contactModel.getPlatform())
                .audience(contactModel.getAudience())
                .sex(contactModel.getSex())
                .region(contactModel.getRegion())
                .language(contactModel.getLanguage())
                .entries(entries)
                .build();
    }
}