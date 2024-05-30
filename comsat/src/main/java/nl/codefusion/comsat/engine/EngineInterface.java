package nl.codefusion.comsat.engine;

import nl.codefusion.comsat.models.ContactModel;

public interface EngineInterface {
    void updateContactChatStatuses();
    void sendTemplateToContacts(ContactModel contact);

}
