package by.tms.shop.services.impl;

import by.tms.shop.exceptions.UploadFailedException;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
@AllArgsConstructor
public class FileService {

    private final Path RELATIVE_PATH = Path.of("./src/main/resources/static/images");

    public void upload(MultipartFile file) {
        try {
            Path destinationFile = RELATIVE_PATH.resolve(
                            Paths.get(Objects.requireNonNull(file.getOriginalFilename())))
                    .normalize().toAbsolutePath();

            InputStream inputStream = file.getInputStream();
            Files.copy(inputStream, destinationFile,
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception exception) {
            throw new UploadFailedException("Загрузка файла невозможна");
        }
    }

    public Path load(String filename) {
        return RELATIVE_PATH.resolve(filename);
    }

    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new UploadFailedException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new UploadFailedException("Could not read file: " + filename);
        }
    }
}