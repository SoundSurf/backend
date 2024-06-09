package com.api.soundsurf.iam;

import com.api.soundsurf.api.utils.S3ImageUploader;
import com.api.soundsurf.iam.exception.CannotCreateQRException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class QrProcessor {

    private final S3ImageUploader s3ImageUploader;

    // TODO: QR 코드 URL 정하기
    private final String baseUrl = "http://" + System.getenv("DB_HOST") + ":" + System.getenv("DB_PORT");

    public String generateQrCode(final String userEmail) {
        final var url = baseUrl + "/api/v1/qr/" + userEmail;

        try {
            byte[] qrCodeImage = generateQrCodeImage(url);
            return uploadQrCodeToS3(qrCodeImage, userEmail);
        } catch (WriterException | IOException e) {
            throw new CannotCreateQRException(e.getMessage());
        }
    }

    private byte[] generateQrCodeImage(final String url) throws WriterException, IOException {
        BitMatrix matrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, 70, 70);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(matrix, "PNG", outputStream);

        return outputStream.toByteArray();
    }

    private String uploadQrCodeToS3(byte[] qrCodeImage, String userEmail) throws IOException {
        String fileName = userEmail + ".png";
        return s3ImageUploader.uploadImage(qrCodeImage, fileName, "qr");
    }
}
