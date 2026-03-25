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

    public String storeFile(MultipartFile file) throws IOException{

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Path filePath = Paths.get(fileStoragePath + "\\" + fileName);

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
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

}