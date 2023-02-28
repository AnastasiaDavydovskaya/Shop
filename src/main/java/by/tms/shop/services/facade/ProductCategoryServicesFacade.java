package by.tms.shop.services.facade;

import by.tms.shop.dto.ProductDto;
import by.tms.shop.services.impl.CategoryService;
import by.tms.shop.services.impl.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class
ProductCategoryServicesFacade {

    private final ProductService productService;
    private final CategoryService categoryService;

    public List<ProductDto> findAllInCategory(Long id) {
        return productService.findByCategory(categoryService.findById(id));
    }
}
