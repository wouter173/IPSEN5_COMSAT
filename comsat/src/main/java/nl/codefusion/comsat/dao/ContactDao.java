package nl.codefusion.comsat.dao;

import nl.codefusion.comsat.models.ContactModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ContactDao extends JpaRepository<ContactModel, UUID> {
}