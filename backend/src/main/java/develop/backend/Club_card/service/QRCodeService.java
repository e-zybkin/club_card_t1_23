package develop.backend.Club_card.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import develop.backend.Club_card.entity.User;
import org.springframework.http.ResponseEntity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public interface QRCodeService {
    static byte[] generateQRCodeImage(String json, int width, int height) throws WriterException, IOException
    {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        BitMatrix bitMatrix = qrCodeWriter.encode(json, BarcodeFormat.QR_CODE, width, height, hints);
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    static String getStringQR(User user){
        try {
            byte[] qrCodeImage = generateQRCodeImage(user.toString(), 300, 300);
            return Base64.getEncoder().encodeToString(qrCodeImage);
        } catch (WriterException | IOException e) {
            return "Error generating QR Code: " + e.getClass().getSimpleName() + " - " + e.getMessage();
        }
    }
    static ResponseEntity<Map<String, String>> encodeQR(User user) {
        try {
            byte[] qrCodeImage = generateQRCodeImage(user.toString(), 300, 300);
            String base64QRCode = Base64.getEncoder().encodeToString(qrCodeImage);
            return ResponseEntity.ok(Map.of("qrCode", base64QRCode));
        } catch (WriterException | IOException e) {
            return ResponseEntity.status(500).body(Map.of("error", "Error generating QR Code: " + e.getMessage()));
        }
    }
}
