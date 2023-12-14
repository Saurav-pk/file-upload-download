package com.example.fileuploaddownload.service;

import com.example.fileuploaddownload.entity.ImageData;
import com.example.fileuploaddownload.repository.StorageRepository;
import com.example.fileuploaddownload.util.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class StorageService {

    @Autowired
    private StorageRepository repository;

    public String uploadImage(MultipartFile file) throws IOException {

        ImageData imageData = repository.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes())).build());
        if (imageData != null) {
            return "file uploaded successfully : " + file.getOriginalFilename();
        }
        return null;
    }

    public byte[] downloadImage(String fileName){
        Optional<ImageData> dbImageData = repository.findByName(fileName);
        byte[] images= ImageUtils.decompressImage(dbImageData.get().getImageData());
        return images;
    }

    public boolean deleteImage(String fileName) {
        Optional<ImageData> dbImageData = repository.findByName(fileName);

        if (dbImageData.isPresent()) {
            repository.delete(dbImageData.get());
            return true; // Deletion successful
        } else {
            return false; // Image not found or could not be deleted
        }
    }

}
