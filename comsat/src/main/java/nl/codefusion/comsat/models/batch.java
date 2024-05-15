package nl.codefusion.comsat.models;

public class batch {
    private String id;
    private String name;
    private String state;
    private String lastModified;
    private String createdAt;

    public void setLastModified(String format) {
        this.lastModified = format;
    }

    public void setState(String processed) {
        this.state = processed;
    }

}
