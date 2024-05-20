package nl.codefusion.comsat.service;

import nl.codefusion.comsat.models.ContactModel;
import nl.codefusion.comsat.repository.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ContactServiceTest {

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ContactService contactService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllContactsReturnsAllContactsFromRepository() {
        ContactModel contact1 = new ContactModel();
        ContactModel contact2 = new ContactModel();
        List<ContactModel> expectedContacts = Arrays.asList(contact1, contact2);

        when(contactRepository.findAll()).thenReturn(expectedContacts);

        List<ContactModel> actualContacts = contactService.getAllContacts();

        assertEquals(expectedContacts, actualContacts);
    }

    @Test
    public void getAllContactsReturnsEmptyListWhenRepositoryIsEmpty() {
        when(contactRepository.findAll()).thenReturn(Arrays.asList());

        List<ContactModel> actualContacts = contactService.getAllContacts();

        assertEquals(0, actualContacts.size());
    }
}