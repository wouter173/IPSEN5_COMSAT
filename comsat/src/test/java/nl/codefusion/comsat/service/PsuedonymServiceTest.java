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
        String pseudonym = pseudonymService.generatePseudonym("Johnny2005", "John", "Kik");
        System.out.println(pseudonym);

        assertTrue(pseudonym.matches("Mars163"));
    }

    @Test
    public void generatePseudonymReturnsValidPlanetNameWithNumber2() {
        String pseudonym = pseudonymService.generatePseudonym("Bearcat68", "Dave", "Snapchat");
        System.out.println(pseudonym);

        assertTrue(pseudonym.matches("Mercury44"));
    }

    @Test
    public void generatePseudonymReturnsValidPlanetNameWithNumber3() {
        String pseudonym = pseudonymService.generatePseudonym("WompRat89", "Luke", "Instagram");
        System.out.println(pseudonym);

        assertTrue(pseudonym.matches("Venus81"));
    }
}