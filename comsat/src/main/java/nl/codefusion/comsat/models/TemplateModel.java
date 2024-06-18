package nl.codefusion.comsat.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Table(name = "templates")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TemplateModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "platform")
    private String platform;

    @Column(name = "header")
    private String header;

    @Column(name = "body", columnDefinition = "TEXT")
    private String body;

    @Column(name = "metadata")
    private String metadata;

    @JsonBackReference
    @OneToMany(mappedBy = "template")
    private List<BatchTemplateEntryModel> batchTemplates;

    @UpdateTimestamp
    @Column(name = "last_modified", nullable = false)
    private Date lastModified;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Date createdAt;
}
