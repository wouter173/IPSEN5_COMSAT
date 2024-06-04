package nl.codefusion.comsat.dao;

import nl.codefusion.comsat.repository.TemplateRepository;
import org.mockito.Mock;
import nl.codefusion.comsat.models.TemplateModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class TemplateDaoTest {

    @Mock
    private TemplateRepository templateRepository;

    @InjectMocks
    private TemplateDao templateDao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createReturnsCreatedTemplate() {
        TemplateModel template = new TemplateModel();
        when(templateRepository.save(template)).thenReturn(template);

        TemplateModel result = templateDao.create(template);

        assertEquals(template, result);
    }

    @Test
    public void updateTemplateUpdatesExistingTemplate() {
        TemplateModel template = new TemplateModel();
        UUID id = UUID.randomUUID();
        template.setId(id);

        TemplateModel existingTemplate = new TemplateModel();
        existingTemplate.setId(template.getId());

        when(templateRepository.findById(template.getId())).thenReturn(Optional.of(existingTemplate));
        when(templateRepository.save(any(TemplateModel.class))).thenAnswer(i -> i.getArguments()[0]);

        templateDao.update(id, template);

        verify(templateRepository).save(template);
    }


    @Test
    public void getTemplateByIdReturnsNullWhenNotPresent() {
        UUID id = UUID.randomUUID();

        when(templateRepository.findById(id)).thenReturn(Optional.empty());

        TemplateModel result = templateDao.getById(id);

        assertNull(result);
    }
}
