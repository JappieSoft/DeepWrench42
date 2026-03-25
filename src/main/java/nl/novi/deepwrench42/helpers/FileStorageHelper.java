package nl.novi.deepwrench42.helpers;

import nl.novi.deepwrench42.exceptions.ReadFileException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class FileStorageHelper {
    private final Path fileStoragePath;

    public FileStorageHelper(@Value("${my.equipment_upload_location}") String fileStorageLocation) throws IOException {
        fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();
        Files.createDirectories(fileStoragePath);
    }

    public String storeFile(String itemId, String existingFileName, MultipartFile file) throws IOException{
        if (itemId.isEmpty()) {
            throw new IllegalArgumentException("No item Id received for File Storage");
        }

        String OriginalName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String fileExtension = ".jpg";
        if (OriginalName.contains(".")) {
            fileExtension = OriginalName.substring(OriginalName.lastIndexOf("."));
        } else {
            throw new IllegalArgumentException("File storage system encountered file name corruption");
        }

        String newFileName = itemId.trim() + fileExtension;
        Path newfilePath = fileStoragePath.resolve(newFileName);
        if (existingFileName != null) {
            Path oldFilePath = fileStoragePath.resolve(existingFileName);
            boolean deleted = Files.deleteIfExists(oldFilePath);
            if  (!deleted) {
                throw new IOException("Unable to delete original file from Server");
            }
        }

        Files.copy(file.getInputStream(), newfilePath, StandardCopyOption.REPLACE_EXISTING);

        return newFileName;
    }

    public Resource downLoadFile(String fileName) {
        Path path = fileStoragePath.resolve(fileName);

        Resource resource;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            throw new ReadFileException("Issue in reading the file", e);
        }

        if(resource.exists()&& resource.isReadable()) {
            return resource;
        } else {
            throw new ReadFileException("the file doesn't exist or not readable");
        }
    }

    public String getMimeType(String fileName) {
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        String mimeType = switch (fileType.toLowerCase()) {
            case "jpg", "jpeg" -> "IMAGE_JPEG_VALUE";
            case "png" -> "IMAGE_PNG_VALUE";
            case "gif" -> "IMAGE_GIF_VALUE";
            case "pdf" -> "APPLICATION_PDF_VALUE";
            default -> "IMAGE_JPEG_VALUE";
        };
        return mimeType;
    }
}