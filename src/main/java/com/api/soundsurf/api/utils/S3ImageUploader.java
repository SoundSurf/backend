package com.api.soundsurf.api.utils;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.api.soundsurf.iam.dto.UserProfileDto;
import com.api.soundsurf.iam.exception.CannotUploadImageException;
import com.api.soundsurf.iam.exception.EmptyFileException;
import com.api.soundsurf.iam.exception.InvalidFileExtensionException;
import com.api.soundsurf.iam.exception.PutObjectException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Component
public class S3ImageUploader {

    private static final Logger LOGGER = Logger.getLogger(S3ImageUploader.class.getName());

    private final AmazonS3 amazonS3;

    @Value("${BUCKET_NAME}")
    private String bucketName;

    public UserProfileDto.Get.Response uploadImage(final MultipartFile image, final String folderPath) {
        if (image.isEmpty() || Objects.isNull(image.getOriginalFilename())) {
            throw new EmptyFileException();
        }
        return new UserProfileDto.Get.Response(validateAndUpload(image, folderPath));
    }

    private String validateAndUpload(final MultipartFile image, final String folderPath) {
        validateImageFileExtension(image.getOriginalFilename());
        try {
            return this.uploadImageToS3(image, folderPath);
        } catch (IOException e) {
            throw new CannotUploadImageException();
        }
    }

    private String uploadImageToS3(final MultipartFile image, final String folderPath) throws IOException {
        final var originalFilename = image.getOriginalFilename();
        final var extension = originalFilename.substring(originalFilename.lastIndexOf("."));

        final var s3FileName = folderPath + "/" + UUID.randomUUID().toString().substring(0, 10) + originalFilename;

        final var is = image.getInputStream();
        byte[] bytes = IOUtils.toByteArray(is);

        final var metadata = new ObjectMetadata();
        metadata.setContentType("image/" + extension);
        metadata.setContentLength(bytes.length);
        final var byteArrayInputStream = new ByteArrayInputStream(bytes);

        try {
            final var putObjectRequest = new PutObjectRequest(bucketName, s3FileName, byteArrayInputStream, metadata);
            amazonS3.putObject(putObjectRequest);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to upload image to S3", e);
            throw new PutObjectException(e.getMessage());
        } finally {
            byteArrayInputStream.close();
            is.close();
        }

        return amazonS3.getUrl(bucketName, s3FileName).toString();
    }

    public String uploadImage(final byte[] imageBytes, final String fileName, final String folderPath) throws IOException {
        final var s3FileName = folderPath + "/" + UUID.randomUUID().toString().substring(0, 10) + fileName;

        final var metadata = new ObjectMetadata();
        metadata.setContentType("image/png");
        metadata.setContentLength(imageBytes.length);
        final var byteArrayInputStream = new ByteArrayInputStream(imageBytes);

        try {
            final var putObjectRequest = new PutObjectRequest(bucketName, s3FileName, byteArrayInputStream, metadata);
            amazonS3.putObject(putObjectRequest);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to upload image to S3", e);
            throw new PutObjectException(e.getMessage());
        } finally {
            byteArrayInputStream.close();
        }

        return amazonS3.getUrl(bucketName, s3FileName).toString();
    }

    private void validateImageFileExtension(final String filename) {
        int lastDotIndex = filename.lastIndexOf(".");
        if (lastDotIndex == -1) {
            throw new InvalidFileExtensionException();
        }

        final var extension = filename.substring(lastDotIndex + 1).toLowerCase();
        final var allowedExtentionList = Arrays.asList("jpg", "jpeg", "png", "gif");

        if (!allowedExtentionList.contains(extension)) {
            throw new InvalidFileExtensionException();
        }
    }
}
