package nl.codefusion.comsat.engine;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.dao.BatchContactEntryDao;
import nl.codefusion.comsat.models.ContactModel;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class KikEngine implements EngineInterface {

    private final BatchContactEntryDao batchContactEntryDao;
    private final Logger logger;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${kik.engine}")
    private String engineUrl;

    @Override
    public void updateContactChatStatuses() {
        ResponseEntity<Map<String, String>> response = restTemplate.exchange(
                engineUrl + "/get_chat_status",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, String>>() {
                }
        );

        Map<String, String> chatStatus = response.getBody();
        if (chatStatus != null) {
            chatStatus.forEach((nickname, status) -> {
                try {
                    logger.info("User: " + nickname + ", Status: " + status);
                    batchContactEntryDao.updateBatchStatusByUsername(nickname, status);

                } catch (NullPointerException e) {
                    logger.warn("No contact found with username: " + nickname);
                }
            });
        }

    }

    @Override
    public void sendTemplateToContacts(ContactModel contact) {
        logger.error("Not implemented");

    }
}
