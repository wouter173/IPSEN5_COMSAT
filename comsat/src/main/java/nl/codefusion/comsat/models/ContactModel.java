package nl.codefusion.comsat.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Table(name = "contact")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ContactModel {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;



    @Column(name = "firstname")
    private String firstName;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "platform")
    private String platform;

    @Column(name = "audience")
    private String audience;

    @Column(name = "sex")
    private String sex;

    @Column(name="language")
    private String language;

    @Column(name = "region")
    private String region;

    @Column(name = "status")
    private String status;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "batch_id", referencedColumnName = "id")
    private BatchModel batch;

}
