package by.tms.shop.mapper;

import by.tms.shop.dto.UserDto;
import by.tms.shop.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "role", constant = "CUSTOMER")
    @Mapping(source = "username", target = "login")
    User toEntity(UserDto userDto);
    @Mapping(source = "login", target = "username")
    UserDto toDto(User user);
}
