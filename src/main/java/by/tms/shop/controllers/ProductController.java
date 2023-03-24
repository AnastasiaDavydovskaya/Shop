package by.tms.shop.controllers;

import by.tms.shop.dto.CategoryDto;
import by.tms.shop.dto.ProductCreatedDto;
import by.tms.shop.dto.ProductDto;
import by.tms.shop.services.facade.ProductCategoryServicesFacade;
import by.tms.shop.services.facade.ProductFileServicesFacade;
import by.tms.shop.services.impl.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class ProductController {

    private CategoryService categoryService;
    private ProductCategoryServicesFacade productCategoryServicesFacade;
    private ProductFileServicesFacade productFileServicesFacade;

    @ModelAttribute("product")
    public ProductDto getProduct() {
        return ProductDto.builder().build();
    }

    @ModelAttribute("productCreatedDto")
    public ProductCreatedDto getProductCreated() {
        return ProductCreatedDto.builder().build();
    }

    @ModelAttribute("category")
    public CategoryDto getCategoryDto() {
        return CategoryDto.builder().build();
    }

    @GetMapping("/category/{id}")
    public String getCategoryProducts(@PathVariable Long id,
                                      Model model) {
        model.addAttribute("page", new PageImpl<>(productCategoryServicesFacade.findAllInCategory(id)));
        model.addAttribute("categories", categoryService.findAll());
        return "customer-page";
    }

    @GetMapping("/add/product")
    public String addProduct(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "add-product";
    }

    @PostMapping("/add/product")
    public String saveProduct(@Valid ProductCreatedDto productCreatedDto, BindingResult bindingResult,
                              @RequestParam("file") MultipartFile file, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return "add-product";
        }

        productFileServicesFacade.saveProduct(file, productCreatedDto);

        return "redirect:/admin/page";
    }
}
