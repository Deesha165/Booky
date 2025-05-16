package com.example.Bookify.controller;


import com.example.Bookify.service.EventService;
import com.example.Bookify.service.ImageStorageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

@RestController
@RequestMapping("api/media")
@AllArgsConstructor
public class ImageStorageController {
   private final ImageStorageService imageStorageService;
   private final EventService eventService;


    @PutMapping("event/{id}/image")
    @ResponseStatus(HttpStatus.CREATED)

    public void uploadEventImage(
            @PathVariable int id,
            @RequestParam("image") MultipartFile imageFile) {


        String imageUrl = imageStorageService.saveImageEvent(id, imageFile);


        eventService.updateEventImageUrl(id, imageUrl);

    }

    @GetMapping("images/{filename:.+}")
    public ResponseEntity<?> serveImage(@PathVariable String filename) {
        try {
            byte[] fileBytes = imageStorageService.loadImageAsBytes(filename);
            String contentType = imageStorageService.getContentType(filename);

            return ResponseEntity.ok()
                    .header("Content-Type",
                            contentType != null ? contentType : "application/octet-stream")
                    .body(fileBytes);

        } catch (IOException e) {
            return ResponseEntity.status(404).body("Could not read image: " + filename);
        }
    }



}
