package nl.codefusion.comsat.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Table(name = "role")
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    @Getter
    private int permissions;
}
