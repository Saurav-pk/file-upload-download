package com.example.fileuploaddownload.repository;

import com.example.fileuploaddownload.entity.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StorageRepository extends JpaRepository<ImageData,Long> {


    Optional<ImageData> findByName(String fileName);

    void delete(Optional<ImageData> imageEntity);

    ImageData findByImageName(String fileName);
}
