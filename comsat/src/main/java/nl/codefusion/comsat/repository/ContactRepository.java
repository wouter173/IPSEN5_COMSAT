package nl.codefusion.comsat.repository;

import nl.codefusion.comsat.models.ContactModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContactRepository extends JpaRepository<ContactModel, UUID> {
}
