package by.tms.shop.controllers;

import by.tms.shop.services.impl.OrderService;
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
public class OrderController {

    private OrderService orderService;

    @GetMapping("/list/orders")
    public String getListOrders(@PageableDefault(size = 5) Pageable pageable, Model model) {
        model.addAttribute("page", orderService.findAllInPage(pageable));
        return "list-orders";
    }

    @GetMapping("/order/delete/{id}")
    public String deleteOrder(@PathVariable Long id, HttpServletRequest request) {
        orderService.deleteById(id);
        return "redirect:" + request.getHeader("Referer");
    }
}
