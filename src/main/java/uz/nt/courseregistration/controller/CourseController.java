package uz.nt.courseregistration.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.nt.courseregistration.domain.dto.request.CourseCreateDTO;
import uz.nt.courseregistration.domain.dto.response.CourseResponseDTO;
import uz.nt.courseregistration.domain.dto.response.UserResponseDTO;
import uz.nt.courseregistration.service.CourseService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public CourseResponseDTO add(@RequestBody CourseCreateDTO createDTO) {
        return courseService.save(createDTO);
    }

    @GetMapping("/get/{id}")
    public CourseResponseDTO getById(@PathVariable Long id) {
        return courseService.getById(id);
    }

    @GetMapping("/curses")
    public List<CourseResponseDTO> getCourseList() {
        return courseService.getAll();
    }

    @PatchMapping("/update/{id}")
    public CourseResponseDTO update(
            @PathVariable Long id,
            @RequestBody CourseCreateDTO createDTO
    ) {
        return courseService.update(createDTO, id);
    }

    @DeleteMapping
    public void delete(@RequestParam(name = "id") Long id) {
        courseService.delete(id);
    }

    @GetMapping("/get/course/users/{id}")
    public List<UserResponseDTO> getCourseUsers(
            @PathVariable Long id
    ){
        return courseService.getCourseUserList(id);
    }

}
