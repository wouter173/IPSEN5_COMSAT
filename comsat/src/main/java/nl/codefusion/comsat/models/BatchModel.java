package nl.codefusion.comsat.models;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity

public class BatchModel {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    @Column(name = "name", nullable = true)
    private String name;
    @Column(name = "state",nullable = false)
    private String state;
    @Column(name = "last_modified", nullable = false)
    private String lastModified;
    @Column(name = "created_at",nullable = false)
    private String createdAt;

    @OneToMany(mappedBy = "batch", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ContactModel> contacts;

    public void setLastModified(String format) {
        this.lastModified = format;
    }

    public void setState(String processed) {
        this.state = processed;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = String.valueOf(createdAt);
    }

    public String getName() {
        return name;
    }

    public void setName(String batch) {
        this.name=name;
    }
}
