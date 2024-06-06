package nl.codefusion.comsat.engine;

import nl.codefusion.comsat.dto.EngineContactDto;
import nl.codefusion.comsat.models.ContactModel;

import java.util.List;

public interface EngineInterface {
    void updateContactChatStatuses();
    void sendTemplateToContacts(List<EngineContactDto> contact);

}
