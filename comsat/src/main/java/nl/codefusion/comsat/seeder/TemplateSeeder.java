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
        templateModel.setHeader("Subject 3");
        templateModel.setBody("Dit is sjabloon 1\n\n Het is heel belangrijk dat de klant geduld heeft en het coachingproces volgt, maar dit is het moment waarop hij met veel werk en pijn te maken krijgt. Of de elitaire selecrisque mauris pellentesque pulvinus pellentesque bewoners. Voor wat salade en een tomatenrecept. \nDe pijn zelf is heel belangrijk, deze wordt door de arts gevolgd, maar het gebeurt op zon moment dat er veel werk en pijn is. Of de elitaire selecrisque mauris pellentesque pulvinus pellentesque bewoners. In feite zijn er veel problemen en veel problemen. Of de elitaire selecrisque mauris pellentesque pulvinus pellentesque bewoners. Voor wat salade en een hete saus.");
        templateModel.setMetadata("Metadata 3");
        templateModel.setCreatedAt(new Date());
        templateModel.setUpdatedAt(new Date());
        templateDao.create(templateModel);

        templateModel = new TemplateModel();
        templateModel.setPlatform("kik");
        templateModel.setHeader("Subject 3");
        templateModel.setBody("Dit is sjabloon 1\n\n Het is heel belangrijk dat de klant geduld heeft en het coachingproces volgt, maar dit is het moment waarop hij met veel werk en pijn te maken krijgt. Of de elitaire selecrisque mauris pellentesque pulvinus pellentesque bewoners. Voor wat salade en een tomatenrecept. \nDe pijn zelf is heel belangrijk, deze wordt door de arts gevolgd, maar het gebeurt op zon moment dat er veel werk en pijn is. Of de elitaire selecrisque mauris pellentesque pulvinus pellentesque bewoners. In feite zijn er veel problemen en veel problemen. Of de elitaire selecrisque mauris pellentesque pulvinus pellentesque bewoners. Voor wat salade en een hete saus.");
        templateModel.setMetadata("Metadata 3");
        templateModel.setCreatedAt(new Date());
        templateModel.setUpdatedAt(new Date());
        templateDao.create(templateModel);

        templateModel = new TemplateModel();
        templateModel.setPlatform("kik");
        templateModel.setHeader("Subject 3");
        templateModel.setBody("Dit is sjabloon 1\n\n Het is heel belangrijk dat de klant geduld heeft en het coachingproces volgt, maar dit is het moment waarop hij met veel werk en pijn te maken krijgt. Of de elitaire selecrisque mauris pellentesque pulvinus pellentesque bewoners. Voor wat salade en een tomatenrecept. \nDe pijn zelf is heel belangrijk, deze wordt door de arts gevolgd, maar het gebeurt op zon moment dat er veel werk en pijn is. Of de elitaire selecrisque mauris pellentesque pulvinus pellentesque bewoners. In feite zijn er veel problemen en veel problemen. Of de elitaire selecrisque mauris pellentesque pulvinus pellentesque bewoners. Voor wat salade en een hete saus.");
        templateModel.setMetadata("Metadata 3");
        templateModel.setCreatedAt(new Date());
        templateModel.setUpdatedAt(new Date());
        templateDao.create(templateModel);
    }

}
