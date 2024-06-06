package nl.codefusion.comsat.repository;

import nl.codefusion.comsat.models.TemplateModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TemplateRepository extends JpaRepository<TemplateModel, UUID> {
}
