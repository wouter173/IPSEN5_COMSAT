package nl.codefusion.comsat.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.dao.UserDao;
import nl.codefusion.comsat.models.UserModel;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserDetailsImplService implements UserDetailsService {

    private final UserDao userDao;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userDao.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return UserModel.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roleModel(user.getRoleModel())
                .build();
    }
}
