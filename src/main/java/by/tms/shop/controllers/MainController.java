package by.tms.shop.controllers;

import by.tms.shop.dto.UserDto;
import by.tms.shop.entities.Role;
import by.tms.shop.services.impl.CategoryService;
import by.tms.shop.services.impl.ProductService;
import by.tms.shop.services.impl.UserService;
import by.tms.shop.utils.AuthUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class MainController {

    private ProductService productService;
    private CategoryService categoryService;
    private UserService userService;

    @ModelAttribute("user")
    public UserDto getUser() {
        return UserDto.builder().build();
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registrationInSystem() {
        return "register";
    }

    @GetMapping("/admin/page")
    public String getAdminPage() {
        return "admin-page";
    }

    @PostMapping("/register")
    public String create(@Valid UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        userService.create(userDto);
        return "redirect:/customer/page";
    }

    @GetMapping("/page")
    public String resolvePage() {
        Role role = AuthUtils.getCurrentUser().getRole();
        if (Role.ADMIN.equals(role)) {
            return "admin-page";
        }

        return "redirect:/customer/page";
    }

    @GetMapping("/")
    public String getMainPage(@PageableDefault(size = 6) Pageable pageable,
                              Model model) {
        model.addAttribute("page", productService.findAllInPage(pageable));
        model.addAttribute("categories", categoryService.findAll());
        return "main-page";
    }

    @GetMapping("/customer/page")
    public String getCustomerPage(@PageableDefault(size = 6) Pageable pageable,
                                  Model model) {
        model.addAttribute("page", productService.findAllInPage(pageable));
        model.addAttribute("categories", categoryService.findAll());
        return "customer-page";
    }
}
