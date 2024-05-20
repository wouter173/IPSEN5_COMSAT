package nl.codefusion.comsat.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private UUID id;

    @Column
    private String firstname;

    @Column
    private String nickname;

    @Column
    private String platform;

    @Column
    private String audience;

    @Column
    private String sex;

    @Column
    private String language;

    @Column
    private String region;

    @ManyToOne
    @JoinColumn(name = "batch_id")
    private BatchModel batch;
}
