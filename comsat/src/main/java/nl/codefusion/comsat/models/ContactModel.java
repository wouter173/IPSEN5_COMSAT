package nl.codefusion.comsat.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Table(name = "moslo")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = true)
    private String firstname;

    @Column(nullable = true)
    private String nickname;

    @Column(nullable = true)
    private String platform;

    @Column(nullable = true)
    private String audience;

    @Column(nullable = true)
    private String sex;

    @Column(nullable = true)
    private String language;

    @Column(nullable = true)
    private String region;

    @ManyToOne
    @JoinColumn(name = "batch_id", nullable = false)
    private BatchModel batch;
}
