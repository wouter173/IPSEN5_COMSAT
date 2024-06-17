package nl.codefusion.comsat.engine;

import nl.codefusion.comsat.dto.EngineContactDto;
import nl.codefusion.comsat.engine.response.EngineStatusResponse;

import java.util.List;

public interface EngineInterface {
    EngineStatusResponse getStatus();

    void updateContactChatStatuses();

    void sendTemplateToContacts(List<EngineContactDto> contact);

}
