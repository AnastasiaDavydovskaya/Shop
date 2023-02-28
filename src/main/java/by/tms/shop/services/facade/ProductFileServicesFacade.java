package by.tms.shop.services.facade;

import by.tms.shop.dto.ProductCreatedDto;
import by.tms.shop.services.impl.FileService;
import by.tms.shop.services.impl.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@AllArgsConstructor
public class ProductFileServicesFacade {

    private final ProductService productService;
    private final FileService fileService;

    public ProductCreatedDto saveProduct(MultipartFile file, ProductCreatedDto productCreatedDto) {
        productService.create(productCreatedDto);
        fileService.upload(file);

        return productCreatedDto;
    }
}
