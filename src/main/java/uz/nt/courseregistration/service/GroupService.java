package uz.nt.courseregistration.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.nt.courseregistration.domain.dto.request.GroupCreateDTO;
import uz.nt.courseregistration.domain.dto.response.GroupResponseDTO;
import uz.nt.courseregistration.domain.entity.course.CourseEntity;
import uz.nt.courseregistration.domain.entity.group.GroupEntity;
import uz.nt.courseregistration.domain.exception.AlreadyExistsException;
import uz.nt.courseregistration.domain.exception.DataNotFoundException;
import uz.nt.courseregistration.repository.CourseRepository;
import uz.nt.courseregistration.repository.GroupRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GroupService implements BaseService<GroupResponseDTO, GroupCreateDTO> {

    private final GroupRepository groupRepository;

    private final CourseRepository courseRepository;

    private final ModelMapper modelMapper;

    @Override
    public GroupResponseDTO save(GroupCreateDTO createDto) {
        CourseEntity courseEntity = courseRepository.findById(createDto.getCourseId()).orElseThrow(() -> {
            throw new DataNotFoundException("No course with this id exists");
        });
        if (isExists(createDto.getName())) {
            throw new AlreadyExistsException("Group with this name already exists");
        }
        GroupEntity groupEntity = modelMapper.map(createDto, GroupEntity.class);
        groupEntity.setEmpty(true);
        groupEntity.setCourse(courseEntity);
        groupEntity = groupRepository.save(groupEntity);
        GroupResponseDTO responseDTO = modelMapper.map(groupEntity, GroupResponseDTO.class);
        responseDTO.setCourseId(groupEntity.getCourse().getId());
        return responseDTO;
    }

    @Override
    public void delete(Long id) {
        groupRepository.deleteById(id);
    }

    @Override
    public GroupResponseDTO update(GroupCreateDTO createDto, Long id) {
        if (groupRepository.findById(id).isEmpty()) {
            throw new DataNotFoundException("This group is not found");
        }
        if (isExists(createDto.getName())) {
            throw new AlreadyExistsException("Group with this name already exists");
        }
        GroupEntity groupEntity = groupRepository.findById(id).get();
        groupEntity.setName(createDto.getName());
        groupEntity.setCapacity(createDto.getCapacity());
        groupEntity = groupRepository.save(groupEntity);
        GroupResponseDTO responseDTO = modelMapper.map(groupEntity, GroupResponseDTO.class);
        responseDTO.setCourseId(groupEntity.getCourse().getId());
        return responseDTO;
    }

    @Override
    public GroupResponseDTO getById(Long id) {
        if (groupRepository.findById(id).isEmpty()) {
            throw new DataNotFoundException("This group is not found");
        }
        GroupEntity groupEntity = groupRepository.findById(id).get();
        GroupResponseDTO responseDTO = modelMapper.map(groupEntity, GroupResponseDTO.class);
        responseDTO.setCourseId(groupEntity.getCourse().getId());
        return responseDTO;
    }

    @Override
    public List<GroupResponseDTO> getAll() {
        List<GroupResponseDTO> groupList = new ArrayList<>();
        for (GroupEntity groupEntity : groupRepository.findAll()) {
            GroupResponseDTO responseDTO = modelMapper.map(groupEntity, GroupResponseDTO.class);
            responseDTO.setCourseId(groupEntity.getCourse().getId());
            groupList.add(responseDTO);
        }
        return groupList;
    }

    public List<GroupResponseDTO> getGroupListByCourseId(Long id) {
        List<GroupResponseDTO> groupList = new ArrayList<>();
        for (GroupResponseDTO responseDTO : getAll()) {
            if (Objects.equals(responseDTO.getCourseId(), id)) {
                groupList.add(responseDTO);
            }
        }
        return groupList;
    }

    private boolean isExists(String name) {
        return groupRepository.findByName(name).isPresent();
    }

    private boolean isCourseExists(Long courseId) {
        for (CourseEntity course : courseRepository.findAll()) {
            if (Objects.equals(course.getId(), courseId)) {
                return true;
            }
        }
        return false;
    }

}
