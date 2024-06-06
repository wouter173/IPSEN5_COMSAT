package nl.codefusion.comsat.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

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
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "contact_id")
    private ContactModel contact;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "batch_id")
    private BatchModel batch;

    @Column(name = "status")
    private String status;

    @Column(name = "hidden")
    private boolean hidden;
}
