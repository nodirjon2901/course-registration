package uz.nt.courseregistration.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.nt.courseregistration.domain.dto.request.UserLoginDTO;
import uz.nt.courseregistration.domain.dto.request.UserRequestDTO;
import uz.nt.courseregistration.domain.dto.response.UserResponseDTO;
import uz.nt.courseregistration.service.UserService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping
    public UserResponseDTO register(@RequestBody UserRequestDTO userRequestDTO) {
        return userService.save(userRequestDTO);
    }

    @GetMapping
    public UserResponseDTO login(@RequestBody UserLoginDTO loginDTO) {
        return userService.login(loginDTO);
    }

}
