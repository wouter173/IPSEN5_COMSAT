package nl.codefusion.comsat.engine;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.dao.BatchContactEntryDao;
import nl.codefusion.comsat.dto.EngineContactDto;
import nl.codefusion.comsat.models.ContactModel;
import nl.codefusion.comsat.service.BatchService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
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
       HttpHeaders headers = new HttpHeaders();
       headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<List<EngineContactDto>> response = restTemplate.exchange(
                engineUrl + "/get_chat_status",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        List<EngineContactDto> engineContactDtos = (List<EngineContactDto>) response.getBody();
        try {
            batchContactEntryDao.updateBatchStatusByUsername(engineContactDtos);
        } catch (Exception e) {
            logger.error("Error updating contact chat statuses", e);
        }


    }

    @Override
    public void sendTemplateToContacts(List<EngineContactDto> contacts) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List<EngineContactDto>> request = new HttpEntity<>(contacts, headers);
        System.out.println(request);

        ResponseEntity<Map<String, String>> response = restTemplate.exchange(
                engineUrl + "/send_message",
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<Map<String, String>>() {
                }
        );
    }
}
