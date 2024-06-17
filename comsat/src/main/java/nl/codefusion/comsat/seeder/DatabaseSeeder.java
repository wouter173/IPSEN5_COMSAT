package nl.codefusion.comsat.seeder;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder {
    private final UserSeeder userSeeder;
    private final ContactSeeder contactSeeder;
    private final TemplateSeeder templateSeeder;
    private final Logger logger;


    @Value("${seed.database:false}")
    private boolean shouldSeed;

    @EventListener
    public void seed(ContextRefreshedEvent ignored) {
        if (!shouldSeed) {
            return;
        }
        this.logger.warn("Seeding database");
        this.userSeeder.seedUsers();
        this.contactSeeder.seedContacts();
        this.templateSeeder.seedTemplates();
    }
}
