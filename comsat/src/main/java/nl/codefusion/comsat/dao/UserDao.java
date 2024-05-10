package nl.codefusion.comsat.dao;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.models.UserModel;
import nl.codefusion.comsat.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserDao {
    private final UserRepository userRepository;


    public Optional<UserModel> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<UserModel> findById(UUID id) {
        return userRepository.findById(id);
    }

    public UserModel create(UserModel user) {
        return userRepository.save(user);
    }

    public void delete(UserModel user) {
        userRepository.delete(user);
    }

    public UserModel update(UUID id, UserModel user) {
        user.setId(id);
        return userRepository.save(user);
    }
}
