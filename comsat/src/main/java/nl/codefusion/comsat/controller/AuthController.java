package nl.codefusion.comsat.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.dto.LoginDto;
import nl.codefusion.comsat.service.JwtService;
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


    @PostMapping(value = "/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody @Valid LoginDto loginDto) {
        String username = loginDto.getUsername();
        String password = loginDto.getPassword();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        UserDetails userDetails = userDetailsImplService.loadUserByUsername(username);
        String token = jwtService.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(Map.of("token", token));
    }
}
