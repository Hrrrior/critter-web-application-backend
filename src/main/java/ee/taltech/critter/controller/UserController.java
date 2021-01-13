package ee.taltech.critter.controller;

import ee.taltech.critter.security.UserSessionHolder;
import ee.taltech.critter.service.users.LoginService;
import ee.taltech.critter.service.users.UserService;
import ee.taltech.critter.service.users.dto.LoginDto;
import ee.taltech.critter.service.users.dto.LoginResponse;
import ee.taltech.critter.service.users.dto.RegisterDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("users")
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private LoginService loginService;

    @PostMapping("register")
    public ResponseEntity<Void> register(@RequestBody RegisterDto registerDto){
        userService.saveUser(registerDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("login")
    public LoginResponse login(@RequestBody LoginDto loginDto){
        return loginService.login(loginDto);
    }

    @GetMapping("me")
    public Object getMe() {
        return UserSessionHolder.getLoggedInUser();
    }
}
