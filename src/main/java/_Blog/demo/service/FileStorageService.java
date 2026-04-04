package _Blog.demo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
public class FileStorageService {

    @Value(value = "${app.storage.upload-dir}")
    private String uploadDirPath;

    public String uploadFile(MultipartFile file, String type, String parentFolder) {
        try {
            Path rootPath = Paths.get(uploadDirPath);
            if (!Files.exists(rootPath)) {
                Files.createDirectory(rootPath);
            }

            String fileName = file.getOriginalFilename();
            String extension = "";
            if (fileName != null && !fileName.isEmpty() && fileName.lastIndexOf(".") != -1) {
                extension = fileName.substring(fileName.lastIndexOf("."));
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The file uploaded has some problems");
            }
            String uniqueFileName = UUID.randomUUID().toString() + extension;
            Path subUploadPath = rootPath.resolve(type).resolve(parentFolder);

            if (!Files.exists(subUploadPath)) {
                Files.createDirectories(subUploadPath);
            }

            Path uniqueFilePath = subUploadPath.resolve(uniqueFileName);

            Files.copy(file.getInputStream(), uniqueFilePath);

            return type +"/" + parentFolder +"/" + uniqueFileName;
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Can't upload this file");
        }
    }

    public String getTypeOfFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String extension = "";
        if (fileName != null && !fileName.isEmpty() && fileName.lastIndexOf(".") != -1) {
            extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The file uploaded has some problems");
        }
        System.out.println("Original file name: " + fileName);
        System.out.println("File extension: " + extension);
        switch (extension) {
            case "png":
            case "jpg":
            case "jpeg":
            case "gif":
                return "image";
            case "mp4":
            case "avi":
            case "mov":
                return "video";
            case "mp3":
            case "wav":
                return "audio";
            default:
                return "document";
        }
    }
}