package by.tms.shop.mapper;

import by.tms.shop.dto.OrderDetailsDto;
import by.tms.shop.entities.OrderDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderDetailsMapper {

    @Mapping(source = "product", target = "product.title")
    OrderDetails toEntity(OrderDetailsDto orderDetailsDto);
    @Mapping(source = "product.title", target = "product")
    OrderDetailsDto toDto(OrderDetails orderDetails);
}
