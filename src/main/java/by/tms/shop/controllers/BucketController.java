package by.tms.shop.controllers;

import by.tms.shop.dto.BucketDto;
import by.tms.shop.entities.User;
import by.tms.shop.repositories.ProductRepository;
import by.tms.shop.services.impl.BucketService;
import by.tms.shop.services.impl.ProductService;
import by.tms.shop.utils.AuthUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
@AllArgsConstructor
public class BucketController {

    private final ProductService productService;
    private BucketService bucketService;

    @GetMapping("/bucket")
    public String bucketByUser(Model model) {
        User currentUser = AuthUtils.getCurrentUser();

        if (currentUser == null) {
            model.addAttribute("bucket", new BucketDto());
        } else {
            model.addAttribute("bucket", bucketService.getBucketByUser(currentUser.getLogin()));
        }

        return "bucket";
    }

    @GetMapping("/bucket/delete/{productId}")
    public String deleteWholeProductInBucket(@PathVariable Long productId) {
        bucketService.deleteProduct(productId, AuthUtils.getCurrentUser().getLogin());
        return "redirect:/bucket";
    }

    @PostMapping("/bucket/{id}")
    public String addToBucket(@PathVariable Long id) {
        productService.addToUserBucket(id, AuthUtils.getCurrentUser().getLogin());
        return "redirect:/customer/page";
    }

    @PostMapping("/bucket")
    public String commitBucket() {
        User currentUser = AuthUtils.getCurrentUser();
        if (currentUser != null) {
            bucketService.commitBucketToOrder(currentUser.getLogin());
        }
        return "redirect:/bucket";
    }

    @GetMapping("/bucket/minus/product/{id}")
    public String minusProduct(@PathVariable Long id) {
        bucketService.minusProduct(id, AuthUtils.getCurrentUser().getLogin());
        return "redirect:/bucket";
    }

    @GetMapping("/bucket/plus/product/{id}")
    public String plusProduct(@PathVariable Long id) {
        productService.addToUserBucket(id, AuthUtils.getCurrentUser().getLogin());
        return "redirect:/bucket";
    }

}
