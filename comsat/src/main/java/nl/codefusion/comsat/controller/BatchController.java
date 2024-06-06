package nl.codefusion.comsat.controller;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.dao.BatchContactEntryDao;
import nl.codefusion.comsat.dao.BatchDao;
import nl.codefusion.comsat.dto.BatchDto;
import nl.codefusion.comsat.dto.BatchResponseContactDto;
import nl.codefusion.comsat.dto.BatchResponseDto;
import nl.codefusion.comsat.models.BatchContactEntryModel;
import nl.codefusion.comsat.models.BatchModel;
import nl.codefusion.comsat.models.ContactModel;
import nl.codefusion.comsat.service.BatchService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequestMapping(value = "/api/v1/batches")
@RestController
@RequiredArgsConstructor
public class BatchController {

    private final BatchService batchService;
    private final BatchDao batchDao;
    private final BatchContactEntryDao batchContactEntryDao;

    @PostMapping()
    public ResponseEntity<String> handleBatch(@Validated @RequestBody BatchDto batchDto) {

        batchService.processBatch(batchDto);

        return ResponseEntity.ok("Batch processed successfully");
    }
    @PostMapping("/{id}/send")
    public ResponseEntity<String> sendBatch(@PathVariable String id){
        batchService.sendBatch(UUID.fromString(id));
        return ResponseEntity.ok("Batch sent successfully");
    }

    @GetMapping()
    public ResponseEntity<List<BatchResponseDto>> getAllBatches() {
        List<BatchModel> batches = batchDao.getAllBatches();

        List<BatchResponseDto> batchesResponse = new ArrayList<>();
        for (BatchModel batch : batches) {
            List<BatchResponseContactDto> contacts = new ArrayList<>();
            for (BatchContactEntryModel contactEntryModel : batchContactEntryDao.findAllByBatchId(batch.getId())) {
                ContactModel contact = contactEntryModel.getContact();

                contacts.add(BatchResponseContactDto.builder()
                        .status(contactEntryModel.getStatus())
                        .sex(contact.getSex())
                        .nickname(contact.getNickname())
                        .platform(contact.getPlatform())
                        .region(contact.getRegion())
                        .language(contact.getLanguage())
                        .id(contact.getId())
                        .firstName(contact.getFirstName())
                        .audience(contact.getAudience())
                        .build());
            }


            BatchResponseDto batchResponseDto = BatchResponseDto.builder()
                    .id(batch.getId())
                    .state(batch.getState())
                    .name(batch.getName())
                    .createdAt(batch.getCreatedAt().toString())
                    .lastModified(batch.getLastModified().toString())
                    .contacts(contacts)
                    .build();

            batchesResponse.add(batchResponseDto);
        }

        return ResponseEntity.ok(batchesResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BatchModel> getBatch(UUID id) {
        BatchModel batch = batchDao.findById(id);
        if (batch == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(batch);
    }
}
