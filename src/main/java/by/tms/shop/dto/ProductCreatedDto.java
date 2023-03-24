package by.tms.shop.dto;

import by.tms.shop.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreatedDto {

    @NotEmpty(message = "Название должно существовать")
    @Size(max = 20, message = "Название не должно превышать 20 символов")
    private String title;
    private String nameOfPhoto;
    @NotNull(message = "Цена должна существовать")
    @Min(value = 20, message = "Цена должна быть не меньше 20")
    private Double price;
    private Category category;
}
