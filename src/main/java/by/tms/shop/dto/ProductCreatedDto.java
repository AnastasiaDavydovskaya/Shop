package by.tms.shop.dto;

import by.tms.shop.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreatedDto {

    private String title;
    private String nameOfPhoto;
    private Double price;
    private Category category;
}
