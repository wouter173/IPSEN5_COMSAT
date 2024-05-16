package nl.codefusion.comsat.models;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Batch {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String state;
    @Column(nullable = false)
    private String lastModified;
    @Column(nullable = false)
    private String createdAt;

    public void setLastModified(String format) {
        this.lastModified = format;
    }

    public void setState(String processed) {
        this.state = processed;
    }

}
