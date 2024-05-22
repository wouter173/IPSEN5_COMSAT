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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
