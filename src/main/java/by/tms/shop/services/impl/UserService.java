package by.tms.shop.services.impl;


import by.tms.shop.dto.UserDto;
import by.tms.shop.entities.Role;
import by.tms.shop.entities.User;
import by.tms.shop.exceptions.ResourceNotFoundException;
import by.tms.shop.mapper.UserMapper;
import by.tms.shop.repositories.UserRepository;
import by.tms.shop.services.CrudService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
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
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(userMapper.toEntity(userDto));

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
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
    }

    @Override
    public void deleteById(Long id) {
         userRepository.deleteById(id);
    }

    @Override
    public UserDto update(UserDto userDto) {
        userRepository.save(userMapper.toEntity(userDto));

        return userDto;
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }
}
