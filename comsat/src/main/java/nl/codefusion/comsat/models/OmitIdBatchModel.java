package nl.codefusion.comsat.models;

import java.util.List;

public class OmitIdBatchModel {
    private String name;
    private String state;
    private String lastModified;
    private String createdAt;
    private List<ContactModel> contacts;

    public List<ContactModel> getContacts() {
        return contacts;
    }

    // getters and setters
}
