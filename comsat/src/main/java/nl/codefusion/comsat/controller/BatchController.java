package nl.codefusion.comsat.controller;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.dao.BatchContactEntryDao;
import nl.codefusion.comsat.dao.BatchDao;
import nl.codefusion.comsat.dao.BatchTemplateEntryDao;
import nl.codefusion.comsat.dao.TemplateDao;
import nl.codefusion.comsat.dto.*;
import nl.codefusion.comsat.models.*;
import nl.codefusion.comsat.service.BatchService;
import nl.codefusion.comsat.service.PermissionService;
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
    private final BatchTemplateEntryDao batchTemplateEntryDao;
    private final TemplateDao templateDao;

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
    public ResponseEntity<BatchResponseDto> updateBatch(@PathVariable UUID id, @RequestBody BatchOptionsDto batchOptionsDto) throws NoPermissionException {
        if (!permissionService.hasPermission(permissionService.getPrincipalRoles(), UPDATE_BATCH)) {
            throw new NoPermissionException();
        }

        BatchModel batch = batchDao.findById(id);
        if (batch == null) {
            return ResponseEntity.notFound().build();
        }

        if (batchOptionsDto.getName() != null) {
            batch.setName(batchOptionsDto.getName());
        }
        if (batchOptionsDto.getTemplates() != null) {
            batchTemplateEntryDao.deleteAllByBatchId(batch.getId());

            List<BatchTemplateEntryModel> templates = new ArrayList<>();
            for (UUID templateId : batchOptionsDto.getTemplates()) {
                TemplateModel template = templateDao.getById(templateId);

                BatchTemplateEntryModel templateEntry = BatchTemplateEntryModel.builder()
                        .template(template)
                        .batch(batch)
                        .build();

                templates.add(batchTemplateEntryDao.create(templateEntry));
            }
            batch.setBatchTemplates(templates);
        }

        BatchModel updatedBatch = batchDao.update(id, batch);

        return ResponseEntity.ok(batchService.batchToDto(updatedBatch));


    }

    @GetMapping()
    public ResponseEntity<List<BatchResponseDto>> getAllBatches() throws NoPermissionException {
        if (permissionService.hasPermission(permissionService.getPrincipalRoles(), READ_BATCH)) {
            List<BatchModel> batches = batchDao.getAllBatches();

            List<BatchResponseDto> batchesResponse = new ArrayList<>();
            for (BatchModel batch : batches) {
                batchesResponse.add(batchService.batchToDto(batch));
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

            return ResponseEntity.ok(batchService.batchToDto(batch));
        }
        throw new NoPermissionException();
    }

    @DeleteMapping("/{batchId}/contacts/{contactId}")
    public ResponseEntity<BatchContactEntryModel> deleteContactFromBatch(@PathVariable UUID batchId, @PathVariable UUID contactId) throws NoPermissionException {
        if (permissionService.hasPermission(permissionService.getPrincipalRoles(), DELETE_BATCH)) {
            BatchContactEntryModel entryModel = batchContactEntryDao.findByBatchIdAndContactId(batchId, contactId);

            BatchContactEntryModel deletedEntryModel = batchContactEntryDao.delete(entryModel.getId());

            return ResponseEntity.ok(deletedEntryModel);
        }
        throw new NoPermissionException();
    }


    @PutMapping("/{batchId}/contacts/{contactId}")
    public ResponseEntity<BatchResponseContactDto> updateContactStatus(@PathVariable UUID batchId, @PathVariable UUID contactId, @RequestBody BatchEntryRequestDto batchEntryRequestDto) throws NoPermissionException {
        if (permissionService.hasPermission(permissionService.getPrincipalRoles(), UPDATE_BATCH)) {
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
