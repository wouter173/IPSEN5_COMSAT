package nl.codefusion.comsat.service;

import nl.codefusion.comsat.models.BatchModel;
import nl.codefusion.comsat.models.ContactModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ContactService {

        public List<ContactModel> generateFakeContacts(int numberOfContacts) {
            List<ContactModel> contacts = new ArrayList<>();
            for (int i = 0; i < numberOfContacts; i++) {
                ContactModel contact = ContactModel.builder()
                        .id(UUID.randomUUID())
                        .firstname("Firstname " + i)
                        .nickname("Nickname " + i)
                        .platform("kik")
                        .audience("Audience " + i)
                        .sex(i % 2 == 0 ? "Male" : "Female")
                        .language("Language " + i)
                        .region("Region " + i)
                        .batch(new BatchModel())
                        .build();
                contacts.add(contact);
            }
            return contacts;
        }
    }
