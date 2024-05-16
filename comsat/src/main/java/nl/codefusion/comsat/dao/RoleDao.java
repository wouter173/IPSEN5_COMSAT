package nl.codefusion.comsat.dao;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.models.RoleModel;
import nl.codefusion.comsat.repository.RoleRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleDao {
    private final RoleRepository roleRepository;

    public RoleModel create(RoleModel roleModel) {
        return roleRepository.save(roleModel);
    }
}
