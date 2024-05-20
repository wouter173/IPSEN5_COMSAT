package nl.codefusion.comsat.controller;

import nl.codefusion.comsat.config.Permission;
import nl.codefusion.comsat.models.ContactModel;
import nl.codefusion.comsat.service.ContactService;
import nl.codefusion.comsat.service.PermissionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import javax.naming.NoPermissionException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class ContactControllerTest {

    @Mock
    private PermissionService permissionService;

    @Mock
    private ContactService contactService;

    @InjectMocks
    private ContactController contactController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getContactsReturnsAllContactsWhenPermissionGranted() throws NoPermissionException {
        ContactModel contact1 = new ContactModel();
        ContactModel contact2 = new ContactModel();
        List<ContactModel> expectedContacts = Arrays.asList(contact1, contact2);

        when(permissionService.hasPermission(permissionService.getPrincipalRoles(), Permission.READ_CONTACT_DETAILS)).thenReturn(true);
        when(contactService.getAllContacts()).thenReturn(expectedContacts);

        ResponseEntity<List<ContactModel>> response = contactController.getContacts();

        assertEquals(expectedContacts, response.getBody());
    }

    @Test
    public void getContactsThrowsNoPermissionExceptionWhenPermissionDenied() {
        when(permissionService.hasPermission(permissionService.getPrincipalRoles(), Permission.READ_CONTACT_DETAILS)).thenReturn(false);

        assertThrows(NoPermissionException.class, () -> contactController.getContacts());
    }
}