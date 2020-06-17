package twitsec.authenticationservice.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import twitsec.authenticationservice.model.User;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class TwoFactorComponent {
    private final GoogleAuthenticator googleAuthenticator;

    String createTOTP(final User user) throws WriterException {
        try {
            final GoogleAuthenticatorKey key = googleAuthenticator.createCredentials(String.valueOf(user.getId()));

            final QRCodeWriter qrCodeWriter = new QRCodeWriter();

            final String otpAuthURL = GoogleAuthenticatorQRGenerator.getOtpAuthTotpURL("TwitSec", user.getEmail(), key);

            final BitMatrix bitMatrix = qrCodeWriter.encode(otpAuthURL, BarcodeFormat.QR_CODE, 200, 200);

            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", byteArrayOutputStream);
            byteArrayOutputStream.close();

            return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
        } catch (WriterException | IOException ex) {
            throw new WriterException("Generating the QR-Code went wrong.");
        }
    }

    boolean validateGoogleAuthenticationCode(final Integer userId, final int authCode) {
        return googleAuthenticator.authorizeUser(userId.toString(), authCode);
    }

}
