package nl.codefusion.comsat.repository;

import nl.codefusion.comsat.models.ContactModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ContactRepository extends JpaRepository<ContactModel, UUID> {
    ContactModel findContactByNickname(String nickname);

    @Query("SELECT c.contact.platform, COUNT(c) FROM BatchContactEntryModel c WHERE (:batchId IS NULL OR c.batch.id = :batchId) GROUP BY c.contact.platform")
    List<Object[]> findPlatfromData(@Param("batchId") UUID batchId);

    @Query("SELECT c.contact.region, COUNT(c) FROM BatchContactEntryModel c WHERE (:batchId IS NULL OR c.batch.id = :batchId) GROUP BY c.contact.region")
    List<Object[]> findRegionData(@Param("batchId") UUID batchId);

    @Query("SELECT c.contact.sex, COUNT(c) FROM BatchContactEntryModel c WHERE (:batchId IS NULL OR c.batch.id = :batchId) GROUP BY c.contact.sex")
    List<Object[]> findGenderData(@Param("batchId") UUID batchId);
}