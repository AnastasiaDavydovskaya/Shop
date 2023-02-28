package by.tms.shop.mapper;

import by.tms.shop.dto.BucketDetailDto;
import by.tms.shop.dto.ProductCreatedDto;
import by.tms.shop.dto.ProductDto;
import by.tms.shop.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(ProductDto productDto);
    Product toEntity(ProductCreatedDto productCreatedDto);
    ProductDto toDto(Product product);
    @Mapping(source = "id", target = "productId")
    @Mapping(target = "amount", constant = "1")
    @Mapping(source = "price", target = "sum")
    BucketDetailDto toBucketDetailDto(Product product);
}
