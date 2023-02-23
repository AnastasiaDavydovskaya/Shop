package by.tms.shop.controllers;

import by.tms.shop.services.impl.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping("/list/users")
    public String getListUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "list-users";
    }

    @GetMapping("/user/delete/{id}")
    public String delete(@PathVariable Long id) {
        userService.deleteById(id);
        return "redirect:/list/users";
    }

}
