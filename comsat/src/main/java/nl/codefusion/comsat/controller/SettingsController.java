package nl.codefusion.comsat.controller;

import dev.samstevens.totp.exceptions.QrGenerationException;
import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.dao.UserDao;
import nl.codefusion.comsat.dto.TotpSettingsDto;
import nl.codefusion.comsat.models.UserModel;
import nl.codefusion.comsat.service.TotpService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/settings")
@RequiredArgsConstructor
public class SettingsController {
    private final TotpService totpService;
    private final UserDao userDao;

    @PostMapping("/totp-qr")
    public ResponseEntity<Map<String, String>> getUserQrCode() throws QrGenerationException {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        UserModel user = userDao.findByUsername(name).orElse(null);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        String secret = totpService.generateSecret();
        user.setTotpSecret(secret);
        userDao.update(user.getId(), user);

        String imageData = totpService.generateQrCode(secret, user.getUsername());

        return ResponseEntity.ok(Map.of("qrCode", imageData));
    }

    @PutMapping("/totp")
    public ResponseEntity<Map<String, Boolean>> updateSettings(@Validated @RequestBody TotpSettingsDto totpSettingsDto) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        UserModel user = userDao.findByUsername(name).orElse(null);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!totpService.validateCode(user.getTotpSecret(), totpSettingsDto.getTotp())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        totpSettingsDto.setMfaEnabled(totpSettingsDto.isMfaEnabled());

        user.setMfaEnabled(totpSettingsDto.isMfaEnabled());
        userDao.update(user.getId(), user);

        return ResponseEntity.ok(Map.of("mfaEnabled", user.isMfaEnabled()));
    }

}
