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
import java.util.Locale;
import java.util.UUID;

@Table(name = "contact")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ContactModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "firstname")
    private String firstName;
    
    @Column(name = "nickname", unique = true)
    private String nickname;

    @Column(name = "platform")
    private String platform;

    @Column(name = "audience")
    private String audience;

    @Column(name = "sex")
    private String sex;

    @Column(name = "language")
    private String language;

    @Column(name = "region")
    private String region;

    @Column(name = "deleted")
    private boolean deleted;

    @JsonBackReference
    @OneToMany(mappedBy = "contact", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<BatchContactEntryModel> batchContacts;

    @UpdateTimestamp
    @Column(name = "last_modified", nullable = false)
    private Date lastModified;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    public void removeBatchFromContact(BatchContactEntryModel batchContact) {
        batchContacts.remove(batchContact);
        batchContact.setBatch(null);
    }
}