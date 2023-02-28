package by.tms.shop.mapper;

import by.tms.shop.dto.OrderDto;
import by.tms.shop.entities.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        uses = {OrderDetailsMapper.class})
public interface OrderMapper {

    @Mapping(source = "orderId", target = "id")
    @Mapping(source = "login", target = "user.login")
    Order toEntity(OrderDto orderDto);
    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "user.login", target = "login")
    OrderDto toDto(Order order);
}
