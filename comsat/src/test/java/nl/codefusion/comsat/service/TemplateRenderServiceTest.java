package nl.codefusion.comsat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import nl.codefusion.comsat.models.ContactModel;
import nl.codefusion.comsat.models.TemplateModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TemplateRenderServiceTest {

    private TemplateRenderService templateRenderService;

    @BeforeEach
    public void setup() {
        templateRenderService = new TemplateRenderService();
    }

    @Test
    public void testRenderTemplateFirstEntryWithPlaceholders() throws JsonProcessingException {
        TemplateModel templateModel = new TemplateModel();
        templateModel.setPlatform("kik");
        templateModel.setHeader("Subject 1");
        templateModel.setBody("[{\"language\":\"dutch\",\"body\":\"<p><span data-placeholdertype=\\\"username\\\" class=\\\"placeholder\\\">username</span>, hallo dit is een test bericht <span data-placeholdertype=\\\"fullname\\\" class=\\\"placeholder\\\">fullname</span></p>\"},{\"language\":\"english\",\"body\":\"\\n\\n It is very important for the customer to be patient, to follow the coaching process, but this is the time that they have to deal with a lot of work and pain. Or the elit selecrisque mauris pellentesque pulvinus pellentesque dwellers. For some salad and a tomato recipe. \\n The pain itself is very important, it will be followed by the doctor, but it happens at such a time that there is a lot of work and pain. Or the elit selecrisque mauris pellentesque pulvinus pellentesque dwellers. In fact, there is a lot of trouble and a lot of trouble. Or the elit selecrisque mauris pellentesque pulvinus pellentesque dwellers. For some salad and a hot sauce.\"},{\"language\":\"spanish\",\"body\":\"\\n\\n Es muy importante que el cliente sea paciente y siga el proceso de coaching, pero este es el momento en el que tiene que lidiar con mucho trabajo y dolor. O los habitantes de elit selecrisque mauris pellentesque pulvinus pellentesque. Para una receta de ensalada y tomate. \\n El dolor en sí es muy importante, será seguido por el médico, pero sucede en un momento en el que hay mucho trabajo y dolor. O los habitantes de elit selecrisque mauris pellentesque pulvinus pellentesque. De hecho, hay muchos problemas y muchos problemas. O los habitantes de elit selecrisque mauris pellentesque pulvinus pellentesque. Para un poco de ensalada y salsa picante.\"}]");
        templateModel.setMetadata("Metadata 3");

        ContactModel contactModel = new ContactModel();
        contactModel.setFirstName("Roman");
        contactModel.setNickname("romantest");

        assertDoesNotThrow(() -> {
            String message = this.templateRenderService.renderTemplate(contactModel, templateModel);
            assertEquals(message, "romantest, hallo dit is een test bericht Roman");
        });
    }

    @Test
    public void testRenderTemplateSpecificLanguage() {
        TemplateModel templateModel = new TemplateModel();
        templateModel.setPlatform("kik");
        templateModel.setHeader("Subject 1");
        templateModel.setBody("[{\"language\":\"dutch\",\"body\":\"<p><span data-placeholdertype=\\\"username\\\" class=\\\"placeholder\\\">username</span>, hallo dit is een test bericht <span data-placeholdertype=\\\"fullname\\\" class=\\\"placeholder\\\">fullname</span></p>\"},{\"language\":\"english\",\"body\":\"<p><span data-placeholdertype=\\\"fullname\\\" class=\\\"placeholder\\\">fullname</span>, English</p>\"},{\"language\":\"spanish\",\"body\":\"\\n\\n Es muy importante que el cliente sea paciente y siga el proceso de coaching, pero este es el momento en el que tiene que lidiar con mucho trabajo y dolor. O los habitantes de elit selecrisque mauris pellentesque pulvinus pellentesque. Para una receta de ensalada y tomate. \\n El dolor en sí es muy importante, será seguido por el médico, pero sucede en un momento en el que hay mucho trabajo y dolor. O los habitantes de elit selecrisque mauris pellentesque pulvinus pellentesque. De hecho, hay muchos problemas y muchos problemas. O los habitantes de elit selecrisque mauris pellentesque pulvinus pellentesque. Para un poco de ensalada y salsa picante.\"}]");
        templateModel.setMetadata("Metadata 3");

        ContactModel contactModel = new ContactModel();
        contactModel.setFirstName("Roman");
        contactModel.setNickname("romantest");
        contactModel.setLanguage("English");

        assertDoesNotThrow(() -> {
            String message = this.templateRenderService.renderTemplate(contactModel, templateModel);
            assertEquals(message, "Roman, English");
        });
    }

    @Test
    public void testRenderTemplateInvalidBody() {
        TemplateModel templateModel = new TemplateModel();
        templateModel.setPlatform("kik");
        templateModel.setHeader("Subject 1");
        templateModel.setBody("[salsa picante.\"}]");
        templateModel.setMetadata("Metadata 3");

        ContactModel contactModel = new ContactModel();
        contactModel.setFirstName("Roman");
        contactModel.setNickname("romantest");
        contactModel.setLanguage("English");

        assertThrows(JsonProcessingException.class, () -> this.templateRenderService.renderTemplate(contactModel, templateModel));
    }
}
