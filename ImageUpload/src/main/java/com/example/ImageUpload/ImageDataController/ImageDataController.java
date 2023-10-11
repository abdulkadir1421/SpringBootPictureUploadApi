package com.example.ImageUpload.ImageDataController;

import com.example.ImageUpload.ImageDataModel.ImageDataModel;
import com.example.ImageUpload.ImageDataRepository.ImageDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
//@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class ImageDataController {

    @Autowired
    private ImageDataRepository imageDataRepository;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // Check if the uploaded file is not empty
            if (file.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please select a file to upload.");
            }

            // Convert MultipartFile to byte array
            byte[] imageData = file.getBytes();

            // Create Image entity and save it to the database
            ImageDataModel image = new ImageDataModel();
            image.setName(file.getOriginalFilename());
            image.setData(imageData);
            imageDataRepository.save(image);

            return ResponseEntity.status(HttpStatus.OK).body("Image uploaded successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image.");
        }
    }
//@GetMapping("/getPic")
//    public void yourNonStaticMethod() {
//        List<ImageDataModel> images = imageDataRepository.findAll();
//
//    }
//@GetMapping("/getPic")
//public ResponseEntity<byte[]> getImageAll() {
////    Optional<ImageDataModel> imageDataOptional = Optional.of((ImageDataModel) imageDataRepository.findAll());
//       List<ImageDataModel> images = imageDataRepository.findAll();
//
//    if (images.isPresent()) {
//        ImageDataModel imageData = image.get();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.IMAGE_JPEG); // Set the appropriate content type based on your image format
//
//        // You might also want to set other headers like content-length if you know the image size
//        // headers.setContentLength(imageData.getData().length);
//
//        // Return the image data along with appropriate headers
//        return new ResponseEntity<>(imageData.getData(), headers, HttpStatus.OK);
//    } else {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("ImageAll not found with id: ").getBytes());
//    }
//}
@DeleteMapping("/deletePic/{id}")
    public ResponseEntity<String> deleteImage(@PathVariable Long id) {
        if (imageDataRepository.existsById(id)) {
            imageDataRepository.deleteById(id);
            return ResponseEntity.ok("Image deleted successfully.");
        } else {
            return ResponseEntity.status(404).body("Image not found with id: " + id);
        }
    }
//
    @GetMapping("/getPicById/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        Optional<ImageDataModel> imageDataOptional = imageDataRepository.findById(id);

        if (imageDataOptional.isPresent()) {
            ImageDataModel imageData = imageDataOptional.get();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // Set the appropriate content type based on your image format

            // You might also want to set other headers like content-length if you know the image size
            // headers.setContentLength(imageData.getData().length);

            // Return the image data along with appropriate headers
            return new ResponseEntity<>(imageData.getData(), headers, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("Image not found with id: " + id).getBytes());
        }
    }
    @PutMapping("/update/{id}")
public ResponseEntity<String> updateImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
    try {
        // Check if the image with the given id exists
        if (!imageDataRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found with id: " + id);
        }

        // Convert MultipartFile to byte array
        byte[] imageData = file.getBytes();

        // Get the existing image from the database
        ImageDataModel image = imageDataRepository.findById(id).orElse(null);
        if (image == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found with id: " + id);
        }

        // Update image data and save it back to the database
        image.setName(file.getOriginalFilename());
        image.setData(imageData);
        imageDataRepository.save(image);

        return ResponseEntity.status(HttpStatus.OK).body("Image updated successfully.");
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update image.");
    }
}

}
