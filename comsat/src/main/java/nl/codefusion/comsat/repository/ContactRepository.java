package nl.codefusion.comsat.repository;

import nl.codefusion.comsat.models.ContactModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ContactRepository extends JpaRepository<ContactModel, UUID> {
    ContactModel findContactByNickname(String nickname);

    @Query("SELECT c.platform, COUNT(c) FROM ContactModel c WHERE (:batchId IS NULL OR c.batch.id = :batchId) GROUP BY c.platform")
    List<Object[]> findPlatfromData(@Param("batchId") UUID batchId);

    @Query("SELECT c.region, COUNT(c) FROM ContactModel c WHERE (:batchId IS NULL OR c.batch.id = :batchId) GROUP BY c.region")
    List<Object[]> findRegionData(@Param("batchId") UUID batchId);

    @Query("SELECT c.sex, COUNT(c) FROM ContactModel c WHERE (:batchId IS NULL OR c.batch.id = :batchId) GROUP BY c.sex")
    List<Object[]> findGenderData(@Param("batchId") UUID batchId);
}