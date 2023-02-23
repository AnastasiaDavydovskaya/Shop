package by.tms.shop.controllers;

import by.tms.shop.services.impl.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;

    @GetMapping("/list/orders")
    public String getListOrders(Model model) {
        model.addAttribute("orders", orderService.findAll());
        return "list-orders";
    }

    @GetMapping("/order/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteById(id);
        return "redirect:/list/orders";
    }
}
