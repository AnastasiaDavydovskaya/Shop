package by.tms.shop.services.impl;

import by.tms.shop.exceptions.UploadFailedException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
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
        if (file != null) {
            String contentType = file.getContentType();
            if (contentType != null && (contentType.equals("image/png")
                    || contentType.equals("image/jpeg")
                    || contentType.equals("image/jpg"))) {
                Path destinationFile = RELATIVE_PATH.resolve(
                                Paths.get(Objects.requireNonNull(file.getOriginalFilename())))
                        .normalize().toAbsolutePath();

                try (InputStream inputStream = file.getInputStream()) {
                    Files.copy(inputStream, destinationFile,
                            StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    throw new UploadFailedException(String.format("Cannot upload file: %s", e.getMessage()));
                }

            }
        }
    }
}
