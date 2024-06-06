package nl.codefusion.comsat.service;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.dao.BatchContactEntryDao;
import nl.codefusion.comsat.dao.BatchDao;
import nl.codefusion.comsat.dao.ContactDao;
import nl.codefusion.comsat.dto.BatchContactDto;
import nl.codefusion.comsat.dto.BatchDto;
import nl.codefusion.comsat.models.BatchContactEntryModel;
import nl.codefusion.comsat.models.BatchModel;
import nl.codefusion.comsat.models.ContactModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class BatchService {

    private final BatchDao batchDao;
    private final BatchContactEntryDao batchContactEntryDao;
    private final ContactDao contactDao;

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
}