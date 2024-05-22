package nl.codefusion.comsat.seeder;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder {
    private final UserSeeder userSeeder;
    private final ContactSeeder contactSeeder;


    private boolean alreadySeeded = false;

    @EventListener
    public void seed(ContextRefreshedEvent ignored) {
        if (alreadySeeded) {
            return;
        }
        this.userSeeder.seedUsers();
        this.contactSeeder.seedContacts();


        this.alreadySeeded = true;
    }
}
