package uz.nt.courseregistration.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.nt.courseregistration.domain.dto.request.GroupCreateDTO;
import uz.nt.courseregistration.domain.dto.response.GroupResponseDTO;
import uz.nt.courseregistration.service.GroupService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/group")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    public GroupResponseDTO add(@RequestBody GroupCreateDTO createDTO) {
        return groupService.save(createDTO);
    }

    @GetMapping("/get/{id}")
    public GroupResponseDTO getById(@PathVariable Long id) {
        return groupService.getById(id);
    }

    @GetMapping("/groups")
    public List<GroupResponseDTO> getGroupList() {
        return groupService.getAll();
    }

    @GetMapping("/groups/{id}")
    public List<GroupResponseDTO> getGroupListByCourseId(
            @PathVariable Long id
    ){
        return groupService.getGroupListByCourseId(id);
    }

    @PatchMapping("/update/{id}")
    public GroupResponseDTO update(
            @PathVariable Long id,
            @RequestBody GroupCreateDTO createDTO
    ) {
        return groupService.update(createDTO, id);
    }

    @DeleteMapping
    public void delete(@RequestParam(name = "id") Long id) {
        groupService.delete(id);
    }

}
