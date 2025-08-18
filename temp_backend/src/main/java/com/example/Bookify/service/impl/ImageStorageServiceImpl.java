package com.example.Bookify.service.impl;

import com.example.Bookify.service.ImageStorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ImageStorageServiceImpl implements ImageStorageService {
    private final Path rootLocation = Paths.get("uploads");


    @Override
    public String saveImageEvent(int eventId, MultipartFile imageFile) {
        try {
            if (imageFile.isEmpty()) {
                throw new RuntimeException("Failed to store empty file.");
            }

            Files.createDirectories(rootLocation);

            String filename = "event_" + eventId + "_" + imageFile.getOriginalFilename();
            Path destinationFile = rootLocation.resolve(filename).normalize();


            Files.copy(imageFile.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);

            return "/api/media/images/" + filename;

        } catch (IOException e) {
            throw new RuntimeException("Failed to store file.", e);
        }
    }

    @Override
    public byte[] loadImageAsBytes(String filename) throws IOException {
        Path file = rootLocation.resolve(filename).normalize();
        if (!Files.exists(file)) {
            throw new IOException("File not found: " + filename);
        }
        return Files.readAllBytes(file);
    }

    @Override
    public String getContentType(String filename) throws IOException {
        Path file = rootLocation.resolve(filename).normalize();
        return Files.probeContentType(file);
    }
}
