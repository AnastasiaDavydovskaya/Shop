package by.tms.shop.services.impl;


import by.tms.shop.dto.UserDto;
import by.tms.shop.entities.User;
import by.tms.shop.exceptions.NotFoundException;
import by.tms.shop.exceptions.ResourceNotFoundException;
import by.tms.shop.mapper.UserMapper;
import by.tms.shop.repositories.UserRepository;
import by.tms.shop.services.CrudService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService implements CrudService<UserDto> {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto create(UserDto userDto) {
        if (userDto.getPassword() != null && userDto.getUsername() != null && isUniqueLogin(userDto)) {
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            userRepository.save(userMapper.toEntity(userDto));
        } else {
            throw new NotFoundException("Пользователь не найден.");
        }

        return userDto;
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден."));
    }

    @Override
    public UserDto deleteById(Long id) {
        UserDto userDto = findById(id);
        userRepository.deleteById(id);

        return userDto;
    }

    @Override
    public UserDto update(UserDto userDto) {
        userRepository.save(userMapper.toEntity(userDto));

        return userDto;
    }

    public User findByLogin(String login) {
        User user = userRepository.findByLogin(login);

        if (user != null) {
            return user;
        } else {
            throw new NotFoundException("Пользователь не найден.");
        }
    }

    public Page<UserDto> findAllInPage(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toDto);
    }

    public boolean isUniqueLogin(UserDto userDto) {
        List<User> userList = userRepository.findAll()
                .stream()
                .filter(user -> !user.getLogin().equals(userDto.getUsername()))
                .toList();

        return userList.size() == userRepository.findAll().size();
    }
}
