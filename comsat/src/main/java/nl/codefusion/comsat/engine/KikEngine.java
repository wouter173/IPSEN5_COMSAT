package nl.codefusion.comsat.engine;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.dao.BatchContactEntryDao;
import nl.codefusion.comsat.dto.EngineContactDto;
import nl.codefusion.comsat.engine.response.EngineGenericResponse;
import nl.codefusion.comsat.engine.response.EngineStatusResponse;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KikEngine implements EngineInterface {

    private final BatchContactEntryDao batchContactEntryDao;
    private final Logger logger;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${kik.engine_url}")
    private String engineUrl;

    public void updateContactChatStatuses() {
        logger.debug("[Poller] running poller");
        try {
            ResponseEntity<List<EngineContactDto>> response = restTemplate.exchange(
                    engineUrl + "/get_chat_status",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    }
            );

            List<EngineContactDto> engineContactDtos = response.getBody();
            try {
                batchContactEntryDao.updateBatchStatusByUsername(engineContactDtos);
            } catch (Exception e) {
                logger.error("Error updating contact chat statuses", e);
            }
        } catch (Exception e) {
            if (e instanceof ResourceAccessException) {
                logger.error("I/O Error getting chat statuses, is the kik engine available on {}?", engineUrl);
            } else {
                logger.error("Error getting chat statuses", e);
            }
        }
    }

    public void sendTemplateToContacts(List<EngineContactDto> contacts) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List<EngineContactDto>> request = new HttpEntity<>(contacts, headers);

        ResponseEntity<Map<String, String>> response = restTemplate.exchange(
                engineUrl + "/send_message",
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<Map<String, String>>() {
                }
        );
    }

    public EngineStatusResponse getStatus() {
        String url = engineUrl + "/status";
        ResponseEntity<EngineStatusResponse> response = restTemplate.getForEntity(url, EngineStatusResponse.class);

        return response.getBody();
    }


    public void solveCaptcha(String token) {
        String url = engineUrl + "/captcha";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(Map.of("token", token), headers);

        ResponseEntity<EngineGenericResponse> response = restTemplate.postForEntity(url, request, EngineGenericResponse.class);

        if (!response.getBody().getStatus().equals("success")) {
            throw new RuntimeException("Failed to solve captcha");
        }
    }
}
