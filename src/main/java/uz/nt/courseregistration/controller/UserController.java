package uz.nt.courseregistration.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.nt.courseregistration.domain.dto.request.UserRequestDTO;
import uz.nt.courseregistration.domain.dto.response.CourseResponseDTO;
import uz.nt.courseregistration.domain.dto.response.UserResponseDTO;
import uz.nt.courseregistration.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/get/{id}")
    public UserResponseDTO getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @GetMapping("/users")
    public List<UserResponseDTO> getUserList() {
        return userService.getAll();
    }

    @PatchMapping("/update/{id}")
    public UserResponseDTO update(
            @PathVariable Long id,
            @RequestBody UserRequestDTO userRequestDTO
    ) {
        return userService.update(userRequestDTO, id);
    }

    @DeleteMapping
    public void delete(@RequestParam(name = "id") Long id) {
        userService.delete(id);
    }

    @PostMapping("/buy/course")
    public CourseResponseDTO buyCourse(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "name") String courseName
    ) {
        return userService.buyCourse(username, courseName);
    }

    @PutMapping("/fillBalance/{id}")
    public UserResponseDTO fillBalance(
            @PathVariable Long id,
            @RequestParam(name = "balance") Double balance
    ) {
        return userService.fillBalance(id, balance);
    }

    @GetMapping("/get/balance/{id}")
    public Double getUserBalance(
            @PathVariable Long id
    ) {
        return userService.getUserBalance(id);
    }

    @GetMapping("/get/user/courses/{id}")
    public List<String> getUserCourses(
            @PathVariable Long id
    ) {
        return userService.getCourseListByUser(id);
    }

}
