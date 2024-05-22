package nl.codefusion.comsat.dao;

import nl.codefusion.comsat.models.BatchModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BatchDao extends JpaRepository<BatchModel, UUID> {
}