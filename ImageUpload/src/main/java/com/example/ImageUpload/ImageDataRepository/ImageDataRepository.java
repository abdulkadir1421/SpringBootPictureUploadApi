package com.example.ImageUpload.ImageDataRepository;

import com.example.ImageUpload.ImageDataModel.ImageDataModel;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ImageDataRepository extends JpaRepository<ImageDataModel,Long> {
}
