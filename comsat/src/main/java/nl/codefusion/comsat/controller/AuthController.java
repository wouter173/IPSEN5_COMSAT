package nl.codefusion.comsat.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.dao.UserDao;
import nl.codefusion.comsat.dto.LoginDto;
import nl.codefusion.comsat.models.UserModel;
import nl.codefusion.comsat.service.JwtService;
import nl.codefusion.comsat.service.TotpService;
import nl.codefusion.comsat.service.UserDetailsImplService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsImplService userDetailsImplService;
    private final UserDao userDao;
    private final TotpService totpService;


    @PostMapping(value = "/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody @Valid LoginDto loginDto) {
        String username = loginDto.getUsername();
        String password = loginDto.getPassword();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        UserDetails userDetails = userDetailsImplService.loadUserByUsername(username);

        UserModel userModel = userDao.findByUsername(userDetails.getUsername()).orElse(null);
        if (userModel == null) {
            return ResponseEntity.status(401).body(Map.of("error", "BAD_CREDENTIALS"));
        }


        if (userModel.isMfaEnabled()) {
            String totp = loginDto.getTotp();
            String secret = userModel.getTotpSecret();

            if (totp == null || totp.isBlank() || totp.length() != 6) {
                return ResponseEntity.status(400).body(Map.of("totp", "Please enter a valid code"));
            }

            if (!totpService.validateCode(secret, totp)) {
                return ResponseEntity.status(401).body(Map.of("error", "TOTP_INVALID"));
            }
        }


        String token = jwtService.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(Map.of("token", token));
    }
}
