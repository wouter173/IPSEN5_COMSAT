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
public class MosloModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String displayName;

    @ManyToOne
    @JoinColumn(name = "batch_id", nullable = false)
    private BatchModel batch;
}
