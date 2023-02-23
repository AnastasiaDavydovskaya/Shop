package by.tms.shop.services.impl;

import by.tms.shop.dto.OrderDto;
import by.tms.shop.entities.Order;
import by.tms.shop.mapper.OrderMapper;
import by.tms.shop.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public Order saveOrder(Order order) {
        orderRepository.save(order);
        return order;
    }

    public List<OrderDto> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }
}