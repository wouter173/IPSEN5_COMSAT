package nl.codefusion.comsat.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.models.UserModel;
import nl.codefusion.comsat.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserDetailsImplService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return UserModel.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .build();

    }
}
