package by.tms.shop.dto;

import by.tms.shop.entities.Bucket;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
public class UserDto {

    private Long id;
    @NotNull(message = "Логин должен сущестовать")
    @Size(min = 7, message = "Логин должен содержать не менее 7 символов")
    private String username;
    @NotNull(message = "Пароль должен существовать")
    @Size(min = 7, message = "Пароль должен содержать не менее 7 символов")
    private String password;
}
