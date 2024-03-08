package uz.nt.courseregistration.domain.dto.response;

import lombok.*;
import uz.nt.courseregistration.domain.entity.course.CourseEntity;
import uz.nt.courseregistration.domain.entity.group.GroupEntity;
import uz.nt.courseregistration.domain.entity.user.UserRole;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {

    private Long id;

    private String username;

    private String password;

    private UserRole role;

    private Double balance;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

}
