package nl.codefusion.comsat.seeder;


import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.dao.TemplateDao;
import nl.codefusion.comsat.models.TemplateModel;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class TemplateSeeder {
    private final TemplateDao templateDao;

    public void seedTemplates() {
        TemplateModel templateModel = new TemplateModel();
        templateModel.setPlatform("kik");
        templateModel.setHeader("Subject 1");
        templateModel.setBody("[{\"language\":\"dutch\",\"body\":\"\\n\\n Het is heel belangrijk dat de klant geduld heeft en het coachingproces volgt, maar dit is het moment waarop hij met veel werk en pijn te maken krijgt. Of de elitaire selecrisque mauris pellentesque pulvinus pellentesque bewoners. Voor wat salade en een tomatenrecept. \\nDe pijn zelf is heel belangrijk, deze wordt door de arts gevolgd, maar het gebeurt op zon moment dat er veel werk en pijn is. Of de elitaire selecrisque mauris pellentesque pulvinus pellentesque bewoners. In feite zijn er veel problemen en veel problemen. Of de elitaire selecrisque mauris pellentesque pulvinus pellentesque bewoners. Voor wat salade en een hete saus.\"},{\"language\":\"english\",\"body\":\"\\n\\n It is very important for the customer to be patient, to follow the coaching process, but this is the time that they have to deal with a lot of work and pain. Or the elit selecrisque mauris pellentesque pulvinus pellentesque dwellers. For some salad and a tomato recipe. \\n The pain itself is very important, it will be followed by the doctor, but it happens at such a time that there is a lot of work and pain. Or the elit selecrisque mauris pellentesque pulvinus pellentesque dwellers. In fact, there is a lot of trouble and a lot of trouble. Or the elit selecrisque mauris pellentesque pulvinus pellentesque dwellers. For some salad and a hot sauce.\"},{\"language\":\"spanish\",\"body\":\"\\n\\n Es muy importante que el cliente sea paciente y siga el proceso de coaching, pero este es el momento en el que tiene que lidiar con mucho trabajo y dolor. O los habitantes de elit selecrisque mauris pellentesque pulvinus pellentesque. Para una receta de ensalada y tomate. \\n El dolor en sí es muy importante, será seguido por el médico, pero sucede en un momento en el que hay mucho trabajo y dolor. O los habitantes de elit selecrisque mauris pellentesque pulvinus pellentesque. De hecho, hay muchos problemas y muchos problemas. O los habitantes de elit selecrisque mauris pellentesque pulvinus pellentesque. Para un poco de ensalada y salsa picante.\"}]");
        templateModel.setMetadata("Metadata 3");
        templateModel.setCreatedAt(new Date());
        templateModel.setUpdatedAt(new Date());
        templateDao.create(templateModel);

        templateModel = new TemplateModel();
        templateModel.setPlatform("kik");
        templateModel.setHeader("Subject 2");
        templateModel.setBody("[{\"language\":\"dutch\",\"body\":\"\\n\\n Het is heel belangrijk dat de klant geduld heeft en het coachingproces volgt, maar dit is het moment waarop hij met veel werk en pijn te maken krijgt. Of de elitaire selecrisque mauris pellentesque pulvinus pellentesque bewoners. Voor wat salade en een tomatenrecept. \\nDe pijn zelf is heel belangrijk, deze wordt door de arts gevolgd, maar het gebeurt op zon moment dat er veel werk en pijn is. Of de elitaire selecrisque mauris pellentesque pulvinus pellentesque bewoners. In feite zijn er veel problemen en veel problemen. Of de elitaire selecrisque mauris pellentesque pulvinus pellentesque bewoners. Voor wat salade en een hete saus.\"},{\"language\":\"english\",\"body\":\"\\n\\n It is very important for the customer to be patient, to follow the coaching process, but this is the time that they have to deal with a lot of work and pain. Or the elit selecrisque mauris pellentesque pulvinus pellentesque dwellers. For some salad and a tomato recipe. \\n The pain itself is very important, it will be followed by the doctor, but it happens at such a time that there is a lot of work and pain. Or the elit selecrisque mauris pellentesque pulvinus pellentesque dwellers. In fact, there is a lot of trouble and a lot of trouble. Or the elit selecrisque mauris pellentesque pulvinus pellentesque dwellers. For some salad and a hot sauce.\"},{\"language\":\"spanish\",\"body\":\"\\n\\n Es muy importante que el cliente sea paciente y siga el proceso de coaching, pero este es el momento en el que tiene que lidiar con mucho trabajo y dolor. O los habitantes de elit selecrisque mauris pellentesque pulvinus pellentesque. Para una receta de ensalada y tomate. \\n El dolor en sí es muy importante, será seguido por el médico, pero sucede en un momento en el que hay mucho trabajo y dolor. O los habitantes de elit selecrisque mauris pellentesque pulvinus pellentesque. De hecho, hay muchos problemas y muchos problemas. O los habitantes de elit selecrisque mauris pellentesque pulvinus pellentesque. Para un poco de ensalada y salsa picante.\"}]");
        templateModel.setMetadata("Metadata 3");
        templateModel.setCreatedAt(new Date());
        templateModel.setUpdatedAt(new Date());
        templateDao.create(templateModel);

        templateModel = new TemplateModel();
        templateModel.setPlatform("kik");
        templateModel.setHeader("Subject 3");
        templateModel.setBody("[{\"language\":\"dutch\",\"body\":\"\\n\\n Het is heel belangrijk dat de klant geduld heeft en het coachingproces volgt, maar dit is het moment waarop hij met veel werk en pijn te maken krijgt. Of de elitaire selecrisque mauris pellentesque pulvinus pellentesque bewoners. Voor wat salade en een tomatenrecept. \\nDe pijn zelf is heel belangrijk, deze wordt door de arts gevolgd, maar het gebeurt op zon moment dat er veel werk en pijn is. Of de elitaire selecrisque mauris pellentesque pulvinus pellentesque bewoners. In feite zijn er veel problemen en veel problemen. Of de elitaire selecrisque mauris pellentesque pulvinus pellentesque bewoners. Voor wat salade en een hete saus.\"},{\"language\":\"english\",\"body\":\"\\n\\n It is very important for the customer to be patient, to follow the coaching process, but this is the time that they have to deal with a lot of work and pain. Or the elit selecrisque mauris pellentesque pulvinus pellentesque dwellers. For some salad and a tomato recipe. \\n The pain itself is very important, it will be followed by the doctor, but it happens at such a time that there is a lot of work and pain. Or the elit selecrisque mauris pellentesque pulvinus pellentesque dwellers. In fact, there is a lot of trouble and a lot of trouble. Or the elit selecrisque mauris pellentesque pulvinus pellentesque dwellers. For some salad and a hot sauce.\"},{\"language\":\"spanish\",\"body\":\"\\n\\n Es muy importante que el cliente sea paciente y siga el proceso de coaching, pero este es el momento en el que tiene que lidiar con mucho trabajo y dolor. O los habitantes de elit selecrisque mauris pellentesque pulvinus pellentesque. Para una receta de ensalada y tomate. \\n El dolor en sí es muy importante, será seguido por el médico, pero sucede en un momento en el que hay mucho trabajo y dolor. O los habitantes de elit selecrisque mauris pellentesque pulvinus pellentesque. De hecho, hay muchos problemas y muchos problemas. O los habitantes de elit selecrisque mauris pellentesque pulvinus pellentesque. Para un poco de ensalada y salsa picante.\"}]");
        templateModel.setMetadata("Metadata 3");
        templateModel.setCreatedAt(new Date());
        templateModel.setUpdatedAt(new Date());
        templateDao.create(templateModel);
    }

}
