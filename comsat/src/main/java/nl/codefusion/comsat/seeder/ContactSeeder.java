package nl.codefusion.comsat.seeder;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.dao.BatchContactEntryDao;
import nl.codefusion.comsat.dao.BatchDao;
import nl.codefusion.comsat.dao.ContactDao;
import nl.codefusion.comsat.models.BatchContactEntryModel;
import nl.codefusion.comsat.models.BatchModel;
import nl.codefusion.comsat.models.ContactModel;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Date;

@RequiredArgsConstructor
@Component
public class ContactSeeder {
    private final ContactDao contactDao;
    private final BatchDao batchDao;
    private final BatchContactEntryDao batchContactEntryDao;
    private final Logger logger;

    public void seedContacts() {
        BatchModel batch = seedBatch();

        seedContact("Roman", "romantest", "kik", "Public", "Male", "English", "North America", batch);
        seedContact("Wouter", "hutsmanhsleiden", "kik", "Private", "Female", "Spanish", "South America", batch);
    }

    private ContactModel seedContact(String firstName, String nickname, String platform, String audience, String sex, String language, String region, BatchModel batch) {

        ContactModel contact = ContactModel.builder()
                .firstName(firstName)
                .nickname(nickname)
                .platform(platform)
                .audience(audience)
                .sex(sex)
                .language(language)
                .region(region)
                .build();

        try {
            ContactModel contactResult = contactDao.create(contact);

            BatchContactEntryModel batchContactEntry = BatchContactEntryModel.builder()
                    .contact(contactResult)
                    .batch(batch)
                    .status("NOTSENT")
                    .build();

            batchContactEntryDao.create(batchContactEntry);

            return contactResult;
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info("Contact {} already exists", contact.getNickname());
            return null;
        }
    }

    private BatchModel seedBatch() {
        BatchModel batch = BatchModel.builder()
                .name("Batch-1")
                .state("NOTSENT")
                .lastModified(new Date())
                .createdAt(new Date())
                .build();

        try {
            return batchDao.create(batch);
        } catch (Exception e) {
            logger.info("Batch {} already exists", batch.getName());
            return null;
        }
    }

}
