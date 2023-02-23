package by.tms.shop.mapper;

import by.tms.shop.dto.CategoryDto;
import by.tms.shop.entities.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toEntity(CategoryDto categoryDto);
    CategoryDto toDto(Category category);
}
