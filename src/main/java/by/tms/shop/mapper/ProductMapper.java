package by.tms.shop.mapper;

import by.tms.shop.dto.ProductCreatedDto;
import by.tms.shop.dto.ProductDto;
import by.tms.shop.entities.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(ProductDto productDto);
    Product toEntity(ProductCreatedDto productCreatedDto);
    ProductDto toDto(Product product);
}
