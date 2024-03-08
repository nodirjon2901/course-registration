package uz.nt.courseregistration.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.nt.courseregistration.domain.dto.request.UserLoginDTO;
import uz.nt.courseregistration.domain.dto.request.UserRequestDTO;
import uz.nt.courseregistration.domain.dto.response.CourseResponseDTO;
import uz.nt.courseregistration.domain.dto.response.UserResponseDTO;
import uz.nt.courseregistration.domain.entity.course.CourseEntity;
import uz.nt.courseregistration.domain.entity.user.UserEntity;
import uz.nt.courseregistration.domain.entity.user.UserRole;
import uz.nt.courseregistration.domain.exception.AlreadyExistsException;
import uz.nt.courseregistration.domain.exception.DataNotFoundException;
import uz.nt.courseregistration.domain.exception.WrongCredentialsException;
import uz.nt.courseregistration.repository.CourseRepository;
import uz.nt.courseregistration.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements BaseService<UserResponseDTO, UserRequestDTO> {

    private final UserRepository userRepository;

    private final CourseRepository courseRepository;

    private final ModelMapper modelMapper;

    @Override
    public UserResponseDTO save(UserRequestDTO createDto) {
        if (isExists(createDto.getUsername())) {
            throw new AlreadyExistsException("This user is already exists");
        }
        UserEntity userEntity = modelMapper.map(createDto, UserEntity.class);
        userEntity.setRole(UserRole.USER);
        userEntity = userRepository.save(userEntity);
        return modelMapper.map(userEntity, UserResponseDTO.class);
    }

    public UserResponseDTO login(UserLoginDTO loginDTO) {
        UserEntity userEntity = userRepository.findByUsername(loginDTO.username()).orElseThrow(() -> {
            throw new WrongCredentialsException("Wrong username and/or password");
        });
        if (Objects.equals(userEntity.getPassword(), loginDTO.password())) {
            return modelMapper.map(userEntity, UserResponseDTO.class);
        }
        throw new WrongCredentialsException("Wrong username and/or password");
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserResponseDTO update(UserRequestDTO createDto, Long id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new DataNotFoundException("This user is not found");
        }
        if (isExists(createDto.getUsername())) {
            throw new AlreadyExistsException("This user is already exists");
        }
        UserEntity userEntity = userRepository.findById(id).get();
        userEntity.setUsername(createDto.getUsername());
        userEntity.setPassword(createDto.getPassword());
        userEntity = userRepository.save(userEntity);
        return modelMapper.map(userEntity, UserResponseDTO.class);
    }

    @Override
    public UserResponseDTO getById(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> {
            throw new DataNotFoundException("This user is not found");
        });
        return modelMapper.map(userEntity, UserResponseDTO.class);
    }

    @Override
    public List<UserResponseDTO> getAll() {
        List<UserResponseDTO> userList = new ArrayList<>();
        for (UserEntity entity : userRepository.findAll()) {
            UserResponseDTO responseDTO = modelMapper.map(entity, UserResponseDTO.class);
            userList.add(responseDTO);
        }
        return userList;
    }

    public CourseResponseDTO buyCourse(String username, String name) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> {
            throw new DataNotFoundException("This user is not found");
        });
        CourseEntity courseEntity = courseRepository.findByName(name).orElseThrow(() -> {
            throw new DataNotFoundException("No course with this name exists");
        });
        if (checkUserCourse(user, name)) {
            throw new AlreadyExistsException("This course is already exists");
        }
        user.getCourses().add(courseEntity);
        courseEntity.getUsers().add(user);
        userRepository.save(user);
        courseRepository.save(courseEntity);
        return modelMapper.map(courseEntity, CourseResponseDTO.class);
    }

    public UserResponseDTO fillBalance(Long id, Double balance) {
        for (UserEntity userEntity : userRepository.findAll()) {
            if (Objects.equals(userEntity.getId(), id)) {
                Double currentBalance = userEntity.getBalance();
                if (currentBalance == null) {
                    currentBalance = 0.0; // Assuming a default value if balance is null
                }
                userEntity.setBalance(currentBalance + balance);
                userEntity = userRepository.save(userEntity);
                return modelMapper.map(userEntity, UserResponseDTO.class);
            }
        }
        throw new DataNotFoundException("User with this id does not exist");
    }


    public Double getUserBalance(Long id) {
        for (UserEntity userEntity : userRepository.findAll()) {
            if (Objects.equals(userEntity.getId(), id)) {
                return userEntity.getBalance();
            }
        }
        throw new DataNotFoundException("User with this id does not exist");
    }

    public List<String> getCourseListByUser(Long id) {
        List<String> courseList = new ArrayList<>();
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> {
            throw new DataNotFoundException("No user with this id exists");
        });
        for (CourseEntity cour : userEntity.getCourses()) {
            courseList.add(cour.getName());
        }
        return courseList;
    }

    private boolean isExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    private boolean checkUserCourse(UserEntity user, String courseName) {
        for (CourseEntity cours : user.getCourses()) {
            if (Objects.equals(cours.getName(), courseName)) {
                return true;
            }
        }
        return false;
    }
}
