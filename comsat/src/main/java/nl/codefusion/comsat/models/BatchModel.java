package nl.codefusion.comsat.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "batch")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    private Date lastModified;

    @Column(name = "created_at",nullable = false)
    private Date createdAt;

    @JsonManagedReference
    @OneToMany(mappedBy = "batch", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ContactModel> contacts;
}

