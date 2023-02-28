package by.tms.shop.controllers;

import by.tms.shop.services.impl.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping("/list/users")
    public String getListUsers(@PageableDefault(size = 5) Pageable pageable, Model model) {
        model.addAttribute("page", userService.findAllInPage(pageable));
        return "list-users";
    }

    @GetMapping("/user/delete/{id}")
    public String delete(@PathVariable Long id, HttpServletRequest request) {
        userService.deleteById(id);
        return "redirect:" + request.getHeader("Referer");
    }

}
