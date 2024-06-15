package nl.codefusion.comsat.controller;

import nl.codefusion.comsat.config.Permission;
import nl.codefusion.comsat.dao.TemplateDao;
import nl.codefusion.comsat.dto.TemplateDto;
import nl.codefusion.comsat.models.TemplateModel;
import nl.codefusion.comsat.service.PermissionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.naming.NoPermissionException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TemplateControllerTest {

    @Mock
    private TemplateDao templateDao;

    @Mock
    private PermissionService permissionService;

    @InjectMocks
    private TemplateController templateController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllTemplatesReturnsAllTemplatesWhenPermissionGranted() throws NoPermissionException {
        TemplateModel template = new TemplateModel();
        List<TemplateModel> expectedTemplates = Collections.singletonList(template);

        when(permissionService.hasPermission(permissionService.getPrincipalRoles(), Permission.READ_TEMPLATE)).thenReturn(true);
        when(templateDao.getAll()).thenReturn(expectedTemplates);

        ResponseEntity<List<TemplateModel>> response = templateController.getAllTemplates();

        assertEquals(expectedTemplates, response.getBody());
    }

    @Test
    public void getAllTemplatesThrowsNoPermissionExceptionWhenPermissionDenied() throws NoPermissionException {
        when(permissionService.hasPermission(permissionService.getPrincipalRoles(), Permission.READ_TEMPLATE)).thenReturn(false);

        Assertions.assertThrows(NoPermissionException.class, () -> {
            templateController.getAllTemplates();
        });
    }
}
