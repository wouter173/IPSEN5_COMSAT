package nl.codefusion.comsat.repository;


import nl.codefusion.comsat.models.RoleModel;



import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface RoleRepository extends JpaRepository<RoleModel, UUID> {

}
