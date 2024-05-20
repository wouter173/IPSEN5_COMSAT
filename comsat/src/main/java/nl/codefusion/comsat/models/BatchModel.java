package nl.codefusion.comsat.models;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity

public class BatchModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String state;
    @Column(nullable = false)
    private String lastModified;
    @Column(nullable = false)
    private String createdAt;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ContactModel> contacts;

    public void setLastModified(String format) {
        this.lastModified = format;
    }

    public void setState(String processed) {
        this.state = processed;
    }

}
