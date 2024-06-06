package nl.codefusion.comsat.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Table(name = "templates")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TemplateModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "platform")
    private String platform;

    @Column(name = "header")
    private String header;

    @Column(name = "body", columnDefinition = "TEXT")
    private String body;

    @Column(name = "metadata")
    private String metadata;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "created_at")
    private Date createdAt;
}
