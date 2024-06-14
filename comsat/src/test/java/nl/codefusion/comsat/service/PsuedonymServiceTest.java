package nl.codefusion.comsat.service;

import nl.codefusion.comsat.service.PseudonymService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PsuedonymServiceTest {

    private PseudonymService pseudonymService;

    @BeforeEach
    public void setup() {
        pseudonymService = new PseudonymService();
    }

    @Test
    public void generatePseudonymReturnsValidPlanetNameWithNumber() {
        String pseudonym = pseudonymService.generatePseudonym();
        System.out.println(pseudonym);

        assertTrue(pseudonym.matches("^(Mercury|Venus|Earth|Mars|Jupiter|Saturn|Neptune)\\d{1,3}$"));
    }
}