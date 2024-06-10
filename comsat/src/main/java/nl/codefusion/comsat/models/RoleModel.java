package nl.codefusion.comsat.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.UUID;

@Table(name = "role")
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "permissions", nullable = false)
    private int permissions;

    @UpdateTimestamp
    @Column(name = "last_modified", nullable = false)
    private Date lastModified;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Date createdAt;
}
