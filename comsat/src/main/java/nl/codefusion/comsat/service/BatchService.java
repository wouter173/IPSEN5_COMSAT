package nl.codefusion.comsat.service;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.dao.BatchContactEntryDao;
import nl.codefusion.comsat.dao.BatchDao;
import nl.codefusion.comsat.dao.ContactDao;
import nl.codefusion.comsat.dto.*;
import nl.codefusion.comsat.engine.KikEngine;
import nl.codefusion.comsat.models.BatchContactEntryModel;
import nl.codefusion.comsat.models.BatchModel;
import nl.codefusion.comsat.models.ContactModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static nl.codefusion.comsat.config.Permission.READ_CONTACT_DETAILS;

@Service
@RequiredArgsConstructor
public class BatchService {

    private final BatchDao batchDao;
    private final BatchContactEntryDao batchContactEntryDao;
    private final ContactDao contactDao;
    private final KikEngine kikEngine;
    private final PseudonymService pseudonymService;
    private final PermissionService permissionService;

    @Transactional
    public void processBatch(BatchDto batchDto) {
        BatchModel batch = new BatchModel();

        batch.setName(batchDto.getName());
        batch.setState("NOTSENT");
        batch.setLastModified(new Date());

        if (batch.getCreatedAt() == null) {
            batch.setCreatedAt(new Date());
        }

        BatchModel newBatch = batchDao.create(batch);

        for (BatchContactDto batchContactDto : batchDto.getContacts()) {
            ContactModel contact = contactDao.findByNickname(batchContactDto.getNickname());
            if (contact == null) {
                contact = contactDao.create(ContactModel.builder()
                        .audience(batchContactDto.getAudience())
                        .firstName(batchContactDto.getFirstName())
                        .nickname(batchContactDto.getNickname())
                        .language(batchContactDto.getLanguage())
                        .region(batchContactDto.getRegion())
                        .platform(batchContactDto.getPlatform())
                        .sex(batchContactDto.getSex())
                        .build());
            }

            BatchContactEntryModel batchContactEntryModel = BatchContactEntryModel.builder()
                    .contact(contact)
                    .batch(newBatch)
                    .status("NOTSENT")
                    .build();

            batchContactEntryDao.create(batchContactEntryModel);
        }

    }

    public void sendBatch(UUID batchId) {
        List<BatchContactEntryModel> batchContactEntries = batchContactEntryDao.findAllByBatchId(batchId);

        List<EngineContactDto> contacts = new ArrayList<>();
        for (BatchContactEntryModel contactEntry : batchContactEntries) {
            if (contactEntry.isHidden()) continue;

            //TODO Template generation
            String msg = "test test";

            contactEntry.setMessage(msg);
            batchContactEntryDao.update(contactEntry.getId(), contactEntry);

            EngineContactDto engineContactDto = EngineContactDto.builder()
                    .batchId(batchId.toString())
                    .message(msg)
                    .username(contactEntry.getContact().getNickname())
                    .build();

            contacts.add(engineContactDto);
        }

        kikEngine.sendTemplateToContacts(contacts);
    }

    public String getBatchState(List<BatchContactEntryModel> contacts) {
        Set<String> statusSet = contacts.stream().filter(x -> !x.isHidden()).map(x -> x.getStatus()).collect(Collectors.toSet());

        String batchStatus = "NOTSENT";
        if (!statusSet.contains("NOTSENT") || statusSet.size() > 1) batchStatus = "SENDING";
        if (statusSet.contains("SENT")) batchStatus = "SENT";

        return batchStatus;
    }

    public BatchResponseDto batchToDto(BatchModel batch) {
        List<BatchContactEntryModel> undeletedContacts = batchContactEntryDao.findAllByBatchId(batch.getId()).stream()
                .filter(entry -> !entry.getContact().isDeleted())
                .toList();

        List<BatchResponseContactDto> contacts = new ArrayList<>();
        for (BatchContactEntryModel contactEntryModel : undeletedContacts) {
            ContactModel contact = contactEntryModel.getContact();
            String firstName;
            String nickname;

            if (permissionService.hasPermission(permissionService.getPrincipalRoles(), READ_CONTACT_DETAILS)) {
                firstName = contact.getFirstName();
                nickname = contact.getNickname();
            } else {
                firstName = pseudonymService.generatePseudonym(contact.getNickname(), contact.getFirstName(), contact.getPlatform());
                nickname = "";
            }


            contacts.add(BatchResponseContactDto.builder()
                    .status(contactEntryModel.getStatus())
                    .hidden(contactEntryModel.isHidden())
                    .sex(contact.getSex())
                    .nickname(nickname)
                    .platform(contact.getPlatform())
                    .region(contact.getRegion())
                    .language(contact.getLanguage())
                    .id(contact.getId())
                    .firstName(firstName)
                    .audience(contact.getAudience())
                    .build());
        }

        List<TemplateDto> templates = batch.getBatchTemplates().stream()
                .map(template -> TemplateDto.builder()
                        .id(template.getTemplate().getId())
                        .platform(template.getTemplate().getPlatform())
                        .header(template.getTemplate().getHeader())
                        .body(template.getTemplate().getBody())
                        .metadata(template.getTemplate().getMetadata())
                        .lastModified(template.getTemplate().getLastModified())
                        .createdAt(template.getTemplate().getCreatedAt())
                        .build())
                .toList();

        return BatchResponseDto.builder()
                .id(batch.getId())
                .state(getBatchState(undeletedContacts))
                .name(batch.getName())
                .createdAt(batch.getCreatedAt())
                .lastModified(batch.getLastModified())
                .contacts(contacts)
                .templates(templates)
                .build();
    }
}