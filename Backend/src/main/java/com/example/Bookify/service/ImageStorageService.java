package com.example.Bookify.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageStorageService {
    String saveImageEvent(int id, MultipartFile imageFile);

    byte[] loadImageAsBytes(String filename) throws IOException;
    String getContentType(String filename) throws IOException;
}
