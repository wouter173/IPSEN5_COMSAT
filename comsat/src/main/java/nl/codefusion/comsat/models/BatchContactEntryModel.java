package nl.codefusion.comsat.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "batch_contact_entry")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BatchContactEntryModel {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "status")
    private String status;

    @Column(name = "hidden")
    private boolean hidden;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "contact_id")
    private ContactModel contact;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "batch_id")
    private BatchModel batch;

    @UpdateTimestamp
    @Column(name = "last_modified", nullable = false)
    private Date lastModified;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Date createdAt;
}
