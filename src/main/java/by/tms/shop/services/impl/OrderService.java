package by.tms.shop.services.impl;

import by.tms.shop.dto.OrderDto;
import by.tms.shop.dto.ProductDto;
import by.tms.shop.entities.Order;
import by.tms.shop.exceptions.NotFoundException;
import by.tms.shop.exceptions.ResourceNotFoundException;
import by.tms.shop.mapper.OrderMapper;
import by.tms.shop.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public Order saveOrder(Order order) {
        if(order != null) {
            orderRepository.save(order);
        } else {
            throw new NotFoundException("Заказ не найден.");
        }

        return order;
    }

    public List<OrderDto> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    public Page<OrderDto> findAllInPage(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(orderMapper::toDto);
    }

    public OrderDto findById(Long id) {
        return orderRepository.findById(id)
                .map(orderMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Заказ не найден."));
    }

    public void deleteById(Long id) {
        OrderDto orderDto = findById(id);
        orderRepository.deleteById(id);
    }
}