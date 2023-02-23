package by.tms.shop.controllers;

import by.tms.shop.dto.CategoryDto;
import by.tms.shop.dto.ProductCreatedDto;
import by.tms.shop.dto.ProductDto;
import by.tms.shop.services.impl.CategoryService;
import by.tms.shop.services.impl.FileService;
import by.tms.shop.services.impl.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@AllArgsConstructor
public class ProductController {

    private ProductService productService;
    private FileService fileService;
    private CategoryService categoryService;

    @ModelAttribute("product")
    public ProductDto getProduct() {
        return ProductDto.builder().build();
    }

    @ModelAttribute("productCreated")
    public ProductCreatedDto getProductCreated() {
        return ProductCreatedDto.builder().build();
    }

    @ModelAttribute("category")
    public CategoryDto getCategoryDto() {
        return CategoryDto.builder().build();
    }

    @GetMapping("/customer/page/{id}")
    public String getCategoryProducts(@PathVariable Long id,
                                      Model model) {
        model.addAttribute("page", new PageImpl<>(productService.findByCategory(categoryService.findById(id))));
        model.addAttribute("categories", categoryService.findAll());
        return "customer-page";
    }

    @GetMapping("/add/product")
    public String addProduct(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "add-product";
    }

    @PostMapping("/add/product")
    public String saveProduct(@RequestParam("file") MultipartFile file, ProductCreatedDto productCreatedDto) {
        productCreatedDto.setNameOfPhoto(file.getOriginalFilename());
        productService.create(productCreatedDto);
        fileService.upload(file);

        return "redirect:/admin/page";
    }
}
