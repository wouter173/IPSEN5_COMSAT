package nl.codefusion.comsat.service;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.dao.ContactDao;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactDao contactDao;

    @Value("${kik.engine}")
    private String engineUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public void updateUserStatuses() {
        ResponseEntity<Map<String, String>> response = restTemplate.exchange(
                engineUrl + "/get_chat_status",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, String>>() {
                }
        );

        Map<String, String> statuses = response.getBody();
        if (statuses != null) {
            statuses.forEach((username, status) -> {
                try {
                    System.out.println("User: " + username + ", Status: " + status);
                    contactDao.updateBatchStatusByUsername(username, status);
                } catch (NullPointerException e) {
                    System.out.println("No contact found with username: " + username);
                }
            });
        }

    }


}
