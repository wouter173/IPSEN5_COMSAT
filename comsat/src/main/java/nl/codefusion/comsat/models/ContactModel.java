package nl.codefusion.comsat.models;

import com.fasterxml.jackson.annotation.JsonFilter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private UUID id;

    @Column(nullable = true)
    private String firstname;

    @Column(nullable = true)
    private String nickname;

    @Column(nullable = true)
    private String platform;

    @Column(nullable = true)
    private String chatStatus;

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