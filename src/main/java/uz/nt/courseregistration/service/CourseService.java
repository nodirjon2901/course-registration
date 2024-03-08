package uz.nt.courseregistration.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.nt.courseregistration.domain.dto.request.CourseCreateDTO;
import uz.nt.courseregistration.domain.dto.response.CourseResponseDTO;
import uz.nt.courseregistration.domain.dto.response.UserResponseDTO;
import uz.nt.courseregistration.domain.entity.course.CourseEntity;
import uz.nt.courseregistration.domain.entity.user.UserEntity;
import uz.nt.courseregistration.domain.exception.AlreadyExistsException;
import uz.nt.courseregistration.domain.exception.DataNotFoundException;
import uz.nt.courseregistration.repository.CourseRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService implements BaseService<CourseResponseDTO, CourseCreateDTO> {

    private final CourseRepository repository;

    private final ModelMapper modelMapper;

    @Override
    public CourseResponseDTO save(CourseCreateDTO createDto) {
        if (isExists(createDto.getName())) {
            throw new AlreadyExistsException("This course is already exists");
        }
        CourseEntity courseEntity = modelMapper.map(createDto, CourseEntity.class);
        courseEntity = repository.save(courseEntity);
        return modelMapper.map(courseEntity, CourseResponseDTO.class);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public CourseResponseDTO update(CourseCreateDTO createDto, Long id) {
        if (repository.findById(id).isEmpty()) {
            throw new DataNotFoundException("This course is not found");
        }
        if (isExists(createDto.getName())) {
            throw new AlreadyExistsException("This course is already exists");
        }
        CourseEntity courseEntity = repository.findById(id).get();
        courseEntity.setName(createDto.getName());
        courseEntity.setPrice(createDto.getPrice());
        courseEntity = repository.save(courseEntity);
        return modelMapper.map(courseEntity, CourseResponseDTO.class);
    }

    @Override
    public CourseResponseDTO getById(Long id) {
        if (repository.findById(id).isEmpty()) {
            throw new DataNotFoundException("This course is not found");
        }
        CourseEntity courseEntity = repository.findById(id).get();
        return modelMapper.map(courseEntity, CourseResponseDTO.class);
    }

    @Override
    public List<CourseResponseDTO> getAll() {
        List<CourseResponseDTO> courseList = new ArrayList<>();
        for (CourseEntity courseEntity : repository.findAll()) {
            CourseResponseDTO responseDTO = modelMapper.map(courseEntity, CourseResponseDTO.class);
            courseList.add(responseDTO);
        }
        return courseList;
    }

    public List<UserResponseDTO> getCourseUserList(Long id) {
        List<UserResponseDTO> userList = new ArrayList<>();
        CourseEntity courseEntity = repository.findById(id).orElseThrow(() -> {
            throw new DataNotFoundException("No course with this id exists");
        });
        for (UserEntity user : courseEntity.getUsers()) {
            userList.add(modelMapper.map(user, UserResponseDTO.class));
        }
        return userList;
    }

    private boolean isExists(String name) {
        return repository.findByName(name).isPresent();
    }

    public CourseEntity getByName(String name) {
        return repository.findByName(name).orElseThrow(() -> {
            throw new DataNotFoundException("This course is not found");
        });
    }

}
