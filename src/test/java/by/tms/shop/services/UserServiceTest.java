package by.tms.shop.services;

import by.tms.shop.dto.UserDto;
import by.tms.shop.entities.User;
import by.tms.shop.entities.enums.Role;
import by.tms.shop.exceptions.ResourceNotFoundException;
import by.tms.shop.mapper.UserMapper;
import by.tms.shop.repositories.UserRepository;
import by.tms.shop.services.impl.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.any;
import static net.bytebuddy.matcher.ElementMatchers.anyOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private UserMapper userMapper;
    @InjectMocks
    private UserService userService;
    private UserDto userDto;
    private User user;

    @BeforeEach
    public void init() {
        userDto = UserDto.builder()
                .id(1L)
                .username("user-login-test")
                .password("user-password-test")
                .build();

        user = User.builder()
                .id(1L)
                .login("user-login-test")
                .password("user-password-test")
                .role(Role.CUSTOMER)
                .build();
    }

    @Test
    void testCreate() {
        UserDto actual = userService.create(userDto);

        assertEquals(userDto, actual);
    }

    @Test
    void testFindAll() {
        when(userRepository.findAll()).thenReturn(List.of());

        List<UserDto> actual = userService.findAll();

        assertEquals(List.of(), actual);
    }

    @Test
    void testFindByIdThrowsNotFound() {
        when(userRepository.findById(anyLong())).thenThrow(new ResourceNotFoundException("Пользователь не найден."));

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            userService.findById(1L);
        });

        assertEquals("Пользователь не найден.", thrown.getMessage());
    }

    @Test
    void testDeleteByIdThrowsNotFound() {
        when(userRepository.findById(anyLong())).thenThrow(new ResourceNotFoundException("Пользователь не найден."));

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            userService.deleteById(1L);
        });

        assertEquals("Пользователь не найден.", thrown.getMessage());
    }

    @Test
    void testUpdate() {
        when(userRepository.save(Mockito.any())).thenReturn(user);

        UserDto actual = userService.update(userDto);

        assertEquals(userDto, actual);
    }

    @Test
    void testFindByLogin() {
        when(userRepository.findByLogin(anyString())).thenReturn(user);

        User actual = userService.findByLogin("user-login-test");

        assertEquals(user, actual);
    }

}
