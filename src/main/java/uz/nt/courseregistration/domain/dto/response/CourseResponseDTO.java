package uz.nt.courseregistration.domain.dto.response;

import lombok.*;
import uz.nt.courseregistration.domain.entity.user.UserRole;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseResponseDTO {

    private Long id;

    private String name;

    private Double price;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

}
