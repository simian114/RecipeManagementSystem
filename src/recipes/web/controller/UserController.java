package recipes.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import recipes.service.UserService;
import recipes.web.dto.UserDto;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping({"/api/register", "/api/register/"})
    ResponseEntity<?> registerUser(@Valid @RequestBody UserDto user) {
        this.userService.addUser(user);
        return ResponseEntity.ok().build();
    }
}
