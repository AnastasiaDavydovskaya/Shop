package by.tms.shop.services;

import by.tms.shop.dto.OrderDto;
import by.tms.shop.entities.Order;
import by.tms.shop.mapper.OrderMapper;
import by.tms.shop.repositories.OrderRepository;
import by.tms.shop.services.impl.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderMapper orderMapper;
    @InjectMocks
    private OrderService orderService;
    private OrderDto orderDto;
    private Order order;

    @BeforeEach
    public void init() {
        orderDto = OrderDto.builder()
                .orderId(1L)
                .login("test-login")
                .sum(1.0)
                .details(List.of())
                .build();

        order = Order.builder()
                .id(1L)
                .sum(BigDecimal.valueOf(1))
                .details(List.of())
                .build();
    }

    @Test
    void testSaveOrder() {
        Order actual = orderService.saveOrder(order);

        assertEquals(order, actual);
    }

    @Test
    void testFindAll() {
        when(orderRepository.findAll()).thenReturn(List.of());

        List<OrderDto> actual = orderService.findAll();

        assertEquals(List.of(), actual);
    }

}
