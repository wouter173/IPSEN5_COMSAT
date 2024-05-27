package nl.codefusion.comsat.seeder;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.dao.BatchDao;
import nl.codefusion.comsat.dao.ContactDao;
import nl.codefusion.comsat.models.BatchModel;
import nl.codefusion.comsat.models.ContactModel;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class ContactSeeder {
    private final ContactDao contactDao;
    private final BatchDao batchDao;

    public void seedContacts() {
        BatchModel batch = seedBatch();
        ContactModel contact = ContactModel.builder()
                .firstname("Roman")
                .nickname("romantest")
                .platform("kik")
                .audience("Public")
                .chatStatus("nothing")
                .sex("Male")
                .language("English")
                .region("North America")
                .batch(batch)
                .build();
        ContactModel contact2 = ContactModel.builder()
                .firstname("Jane")
                .nickname("Janey")
                .platform("kik")
                .audience("Private")
                .sex("Female")
                .language("Spanish")
                .region("South America")
                .batch(batch)
                .build();
        ContactModel contact3 = ContactModel.builder()
                .firstname("Bob")
                .nickname("Bobby")
                .platform("kik")
                .audience("Public")
                .sex("Male")
                .language("French")
                .region("Europe")
                .batch(batch)
                .build();
        ContactModel contact4 = ContactModel.builder()
                .firstname("Alice")
                .nickname("Ally")
                .platform("kik")
                .audience("Private")
                .sex("Female")
                .language("German")
                .region("Europe")
                .batch(batch)
                .build();
        ContactModel contact5 = ContactModel.builder()
                .firstname("Charlie")
                .nickname("Chuck")
                .platform("kik")
                .audience("Public")
                .sex("Male")
                .language("Italian")
                .region("Europe")
                .batch(batch)
                .build();
        ContactModel contact6 = ContactModel.builder()
                .firstname("Diana")
                .nickname("Di")
                .platform("kik")
                .audience("Private")
                .sex("Female")
                .language("Portuguese")
                .region("South America")
                .batch(batch)
                .build();
        ContactModel contact7 = ContactModel.builder()
                .firstname("Ethan")
                .nickname("E")
                .platform("kik")
                .audience("Public")
                .sex("Male")
                .language("Russian")
                .region("Asia")
                .batch(batch)
                .build();
        ContactModel contact8 = ContactModel.builder()
                .firstname("Fiona")
                .nickname("Fi")
                .platform("kik")
                .audience("Private")
                .sex("Female")
                .language("Chinese")
                .region("Asia")
                .batch(batch)
                .build();
        ContactModel contact9 = ContactModel.builder()
                .firstname("George")
                .nickname("Geo")
                .platform("kik")
                .audience("Public")
                .sex("Male")
                .language("Japanese")
                .region("Asia")
                .batch(batch)
                .build();
        ContactModel contact10 = ContactModel.builder()
                .firstname("Hannah")
                .nickname("Han")
                .platform("kik")
                .audience("Private")
                .sex("Female")
                .language("Korean")
                .region("Asia")
                .batch(batch)
                .build();


        contactDao.create(contact);
        contactDao.create(contact2);
        contactDao.create(contact3);
        contactDao.create(contact4);
        contactDao.create(contact5);
        contactDao.create(contact6);
        contactDao.create(contact7);
        contactDao.create(contact8);
        contactDao.create(contact9);
        contactDao.create(contact10);
    }

    private BatchModel seedBatch() {

        BatchModel batch = BatchModel.builder()
                .name("Batch1")
                .lastModified(new Date())
                .createdAt(new Date())
                .build();
        return batchDao.create(batch);
    }

}
