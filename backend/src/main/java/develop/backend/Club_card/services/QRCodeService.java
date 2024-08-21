package develop.backend.Club_card.services;

import com.google.zxing.WriterException;

import java.io.IOException;

public interface QRCodeService {

    static byte[] generateQRCodeImage(String json, int width, int height) throws WriterException, IOException {
        return new byte[0];
    }

}
