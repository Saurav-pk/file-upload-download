package com.example.fileuploaddownload.controller;

import com.example.fileuploaddownload.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.ImagingOpException;
import java.io.IOException;

@RestController
@RequestMapping("/image")
public class StorageController {

    @Autowired
    private StorageService service;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) throws ImagingOpException, IOException {
        String uploadImage = service.uploadImage(file);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName){
        byte[] imageData=service.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }

    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<?> deleteImage(@PathVariable String fileName) {
        try {
            boolean deleted = service.deleteImage(fileName);

            if (deleted) {
                return ResponseEntity.status(HttpStatus.OK).body("Image deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found or could not be deleted");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting image");
        }
    }
}
