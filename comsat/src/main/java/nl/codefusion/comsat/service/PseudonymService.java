package nl.codefusion.comsat.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class PseudonymService {

    private String[] planets = {"Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Neptune"};

    public String  generatePseudonym(String nicknname, String firstName, String platform) {
        int calculatePlanet = firstName.length() + nicknname.length() + platform.length();
        calculatePlanet = calculatePlanet % planets.length;
        if (calculatePlanet > 6) {
            calculatePlanet = 0;
        }
        String pseudonym;
        pseudonym = planets[calculatePlanet];
        pseudonym = pseudonym + (firstName.length() * nicknname.length() * (calculatePlanet + 1) + platform.length());

        return pseudonym;
    }
}
