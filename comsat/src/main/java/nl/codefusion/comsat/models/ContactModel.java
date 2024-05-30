package nl.codefusion.comsat.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFilter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "contact")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
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