package by.tms.shop.controllers;

import by.tms.shop.dto.UserDto;
import by.tms.shop.services.impl.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "User", description = "User API")
@RestController
@RequestMapping("/rest/user")
@AllArgsConstructor
public class UserRestController {

    private UserService userService;

    @GetMapping
    @Operation(summary = "Get all users")
    public List<UserDto> getAll() {
        return userService.findAll();
    }

    @PostMapping
    @Operation(summary = "Create user")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody UserDto userDto) {
        userService.create(userDto);
    }

    @GetMapping("{id}")
    @Operation(summary = "Get user by id")
    public UserDto findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.deleteById(id);
    }

    @PatchMapping
    @Operation(summary = "Update user login and password")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserDto update(@RequestBody UserDto userDto) {
        return userService.update(userDto);
    }

}
