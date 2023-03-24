package by.tms.shop.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
public class UserDto {

    private Long id;
    @NotEmpty(message = "Логин должен существовать")
    @Size(min = 7, message = "Логин должен содержать не менее 7 символов")
    @Email(message = "Логин должен быть почтой")
    private String username;
    @NotEmpty(message = "Пароль должен существовать")
    @Size(min = 7, message = "Пароль должен содержать не менее 7 символов")
    private String password;
}
