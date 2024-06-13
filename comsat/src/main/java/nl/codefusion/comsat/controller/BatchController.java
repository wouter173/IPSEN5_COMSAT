package nl.codefusion.comsat.controller;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.dao.BatchContactEntryDao;
import nl.codefusion.comsat.dao.BatchDao;
import nl.codefusion.comsat.dto.*;
import nl.codefusion.comsat.models.BatchContactEntryModel;
import nl.codefusion.comsat.models.BatchModel;
import nl.codefusion.comsat.models.ContactModel;
import nl.codefusion.comsat.service.BatchService;
import nl.codefusion.comsat.service.PermissionService;
import nl.codefusion.comsat.service.PseudonymService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.naming.NoPermissionException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static nl.codefusion.comsat.config.Permission.*;

@RequestMapping(value = "/api/v1/batches")
@RestController
@RequiredArgsConstructor
public class BatchController {

    private final BatchService batchService;
    private final BatchDao batchDao;
    private final BatchContactEntryDao batchContactEntryDao;
    private final PermissionService permissionService;
    private final PseudonymService pseudonymService;

    @PostMapping()
    public ResponseEntity<String> handleBatch(@Validated @RequestBody BatchDto batchDto) throws NoPermissionException {
    if (permissionService.hasPermission(permissionService.getPrincipalRoles(), CREATE_BATCH)) {
            batchService.processBatch(batchDto);
            return ResponseEntity.ok("Batch processed successfully");
        }
        throw new NoPermissionException();
    }

    @PostMapping("/{id}/send")
    public ResponseEntity<String> sendBatch(@PathVariable String id) {

        batchService.sendBatch(UUID.fromString(id));
        return ResponseEntity.ok("Batch sent successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<BatchModel> updateBatchName(@PathVariable UUID id, @RequestBody BatchOptionsDto batchOptionsDto) throws NoPermissionException{
        if (permissionService.hasPermission(permissionService.getPrincipalRoles(), UPDATE_BATCH)) {
            BatchModel batch = batchDao.findById(id);
            if (batch == null) {
                return ResponseEntity.notFound().build();
            }

            batch.setName(batchOptionsDto.getName());
            BatchModel updatedBatch = batchDao.update(id, batch);

            return ResponseEntity.ok(updatedBatch);
        }
        throw new NoPermissionException();
    }

    @GetMapping()
    public ResponseEntity<List<BatchResponseDto>> getAllBatches() throws NoPermissionException {
        if (permissionService.hasPermission(permissionService.getPrincipalRoles(), READ_BATCH)) {
        List<BatchModel> batches = batchDao.getAllBatches();

        List<BatchResponseDto> batchesResponse = new ArrayList<>();
        for (BatchModel batch : batches) {
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
                    firstName = pseudonymService.generatePseudonym();
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

            BatchResponseDto batchResponseDto = BatchResponseDto.builder()
                    .id(batch.getId())
                    .state(batchService.getBatchState(undeletedContacts))
                    .name(batch.getName())
                    .createdAt(batch.getCreatedAt().toString())
                    .lastModified(batch.getLastModified().toString())
                    .contacts(contacts)
                    .build();

            batchesResponse.add(batchResponseDto);
        }

        return ResponseEntity.ok(batchesResponse);
        }
        throw new NoPermissionException();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BatchResponseDto> getBatch(UUID id) throws NoPermissionException {
        if (permissionService.hasPermission(permissionService.getPrincipalRoles(), READ_BATCH)) {
            BatchModel batch = batchDao.findById(id);
            if (batch == null) {
                return ResponseEntity.notFound().build();
            }


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
                    firstName = pseudonymService.generatePseudonym();
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

            BatchResponseDto batchResponseDto = BatchResponseDto.builder()
                    .id(batch.getId())
                    .state(batchService.getBatchState(undeletedContacts))
                    .name(batch.getName())
                    .createdAt(batch.getCreatedAt().toString())
                    .lastModified(batch.getLastModified().toString())
                    .contacts(contacts)
                    .build();


            return ResponseEntity.ok(batchResponseDto);
        }
        throw new NoPermissionException();
    }

    @DeleteMapping("/{batchId}/contacts/{contactId}")
    public ResponseEntity<BatchContactEntryModel> deleteContactFromBatch(@PathVariable UUID batchId, @PathVariable UUID contactId) throws NoPermissionException{
        if (permissionService.hasPermission(permissionService.getPrincipalRoles(), DELETE_BATCH)) {
            BatchContactEntryModel entryModel = batchContactEntryDao.findByBatchIdAndContactId(batchId, contactId);

            BatchContactEntryModel deletedEntryModel = batchContactEntryDao.delete(entryModel.getId());

            return ResponseEntity.ok(deletedEntryModel);
        }
        throw new NoPermissionException();
    }


    @PutMapping("/{batchId}/contacts/{contactId}")
    public ResponseEntity<BatchResponseContactDto> updateContactStatus(@PathVariable UUID batchId, @PathVariable UUID contactId, @RequestBody BatchEntryRequestDto batchEntryRequestDto) throws NoPermissionException {
        if (permissionService.hasPermission(permissionService.getPrincipalRoles(), UPDATE_BATCH)){
            BatchContactEntryModel entryModel = batchContactEntryDao.findByBatchIdAndContactId(batchId, contactId);

            entryModel.setHidden(batchEntryRequestDto.getHidden());

            BatchContactEntryModel contactEntryModel = batchContactEntryDao.update(entryModel.getId(), entryModel);
            ContactModel contact = contactEntryModel.getContact();

            BatchResponseContactDto batchResponseContactDto = BatchResponseContactDto.builder()
                    .status(contactEntryModel.getStatus())
                    .hidden(contactEntryModel.isHidden())
                    .sex(contact.getSex())
                    .nickname(contact.getNickname())
                    .platform(contact.getPlatform())
                    .region(contact.getRegion())
                    .language(contact.getLanguage())
                    .id(contact.getId())
                    .firstName(contact.getFirstName())
                    .audience(contact.getAudience())
                    .build();

            return ResponseEntity.ok(batchResponseContactDto);
        }
        throw new NoPermissionException();
    }
}
