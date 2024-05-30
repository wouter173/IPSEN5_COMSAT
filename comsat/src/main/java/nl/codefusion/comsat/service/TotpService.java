package nl.codefusion.comsat.service;

import dev.samstevens.totp.code.*;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.QrGenerator;
import dev.samstevens.totp.qr.ZxingPngQrGenerator;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.secret.SecretGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;
import org.springframework.stereotype.Service;

import static dev.samstevens.totp.util.Utils.getDataUriForImage;

@Service
public class TotpService {
    @SuppressWarnings("FieldCanBeLocal")
    private final String issuer = "Spine Comsat";
    private final HashingAlgorithm algorithm = HashingAlgorithm.SHA1;

    private final TimeProvider timeProvider = new SystemTimeProvider();
    private final CodeGenerator codeGenerator = new DefaultCodeGenerator(algorithm);
    private final CodeVerifier verifier = new DefaultCodeVerifier(codeGenerator, timeProvider);

    public String generateSecret() {
        SecretGenerator secretGenerator = new DefaultSecretGenerator();
        return secretGenerator.generate();
    }

    public String generateQrCode(String secret, String email) throws QrGenerationException {
        QrData qrData = new QrData.Builder()
                .secret(secret)
                .issuer(issuer)
                .label(email)
                .algorithm(algorithm)
                .digits(6)
                .build();

        QrGenerator generator = new ZxingPngQrGenerator();
        byte[] imgData = generator.generate(qrData);
        String mimeType = generator.getImageMimeType();

        @SuppressWarnings("UnnecessaryLocalVariable")
        String imgString = getDataUriForImage(imgData, mimeType);

        return imgString;
    }

    public boolean validateCode(String secret, String code) {
        return verifier.isValidCode(secret, code);
    }
}
