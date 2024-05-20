package nl.codefusion.comsat.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "batch")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BatchModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String state;
    @Column(nullable = false)
    private String lastModified;
    @Column(nullable = false)
    private String createdAt;

}