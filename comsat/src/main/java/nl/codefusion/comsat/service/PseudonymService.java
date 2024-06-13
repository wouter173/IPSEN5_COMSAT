package nl.codefusion.comsat.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PseudonymService {

    private String[] planets = {"Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Neptune"};

    public String  generatePseudonym() {
        String pseudonym;
        pseudonym = planets[(int) (Math.random() * planets.length)];
        pseudonym = pseudonym + (int) (Math.random() * 1000);

        return pseudonym;
    }
}
